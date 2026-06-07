package ws.mia.poseidon.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import ws.mia.poseidon.api.model.PoseidonContainer;
import ws.mia.poseidon.api.model.PoseidonContainerEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class PoseidonHttpClient implements PoseidonClient {

	private final HttpClient httpClient;
	private final ObjectMapper objectMapper;
	private final String baseUrl;

	public PoseidonHttpClient(String baseUrl) {
		this(baseUrl, HttpClient.newBuilder()
				.connectTimeout(Duration.ofSeconds(10))
				.build());
	}

	public PoseidonHttpClient(String baseUrl, HttpClient httpClient) {
		this.baseUrl = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
		this.httpClient = httpClient;
		this.objectMapper = new ObjectMapper();
	}

	private HttpRequest.Builder getHttpBuilder(String endpoint) {
		if (endpoint.startsWith("/")) endpoint = endpoint.substring(1);

		return HttpRequest.newBuilder()
				.uri(URI.create(baseUrl + "/api/" + endpoint));
	}

	@Override
	public List<PoseidonContainer> getContainers() {
		try {
			HttpRequest request = getHttpBuilder("containers")
					.GET()
					.header("Accept", "application/json")
					.build();

			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

			if (isSuccess(response.statusCode())) {
				return objectMapper.readValue(response.body(), new TypeReference<List<PoseidonContainer>>() {
				});
			}

			throw new PoseidonClientException("Received non-2xx response from Poseidon (%s): %s"
					.formatted(response.statusCode(), response.body()));
		} catch (IOException e) {
			throw new PoseidonClientException("Failed to get containers", e);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new PoseidonClientException("Failed to get containers", e);
		}
	}

	@Override
	public String getVersion() {
		try {
			HttpRequest request = getHttpBuilder("version")
					.GET()
					.header("Accept", "text/plain")
					.build();

			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

			if (isSuccess(response.statusCode())) {
				return response.body();
			}

			throw new PoseidonClientException("Received non-2xx response from Poseidon (%s): %s"
					.formatted(response.statusCode(), response.body()));
		} catch (IOException e) {
			throw new PoseidonClientException("Failed to get version", e);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new PoseidonClientException("Failed to get version", e);
		}
	}

	@Override
	public Runnable subscribeToEvents(Consumer<PoseidonContainerEvent> onEvent, Consumer<Throwable> onError) {
		AtomicBoolean cancelled = new AtomicBoolean(false);

		Thread thread = new Thread(() -> {
			try {
				HttpRequest request = getHttpBuilder("containers/event-stream")
						.GET()
						.header("Accept", "text/event-stream")
						.build();

				HttpResponse<java.io.InputStream> response = httpClient.send(
						request, HttpResponse.BodyHandlers.ofInputStream());

				if (!isSuccess(response.statusCode())) {
					onError.accept(new PoseidonClientException(
							"Received non-2xx response from Poseidon SSE (%s)".formatted(response.statusCode())));
					return;
				}

				try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.body()))) {
					String eventName = null;
					StringBuilder dataBuffer = new StringBuilder();

					String line;
					while (!cancelled.get() && (line = reader.readLine()) != null) {
						if (line.startsWith("event:")) {
							eventName = line.substring("event:".length()).trim();
						} else if (line.startsWith("data:")) {
							dataBuffer.append(line.substring("data:".length()).trim());
						} else if (line.trim().isEmpty() && !dataBuffer.isEmpty()) {
							try {
								JsonNode node = objectMapper.readTree(dataBuffer.toString());
								PoseidonContainer container = objectMapper.treeToValue(node.get("container"), PoseidonContainer.class);
								String evtName = node.has("eventName") ? node.get("eventName").asText() : eventName;
								onEvent.accept(new PoseidonContainerEvent(evtName, container));
							} catch (Exception e) {
								onError.accept(new PoseidonClientException("Failed to parse SSE event", e));
							}
							eventName = null;
							dataBuffer.setLength(0);
						}
					}
				}
			} catch (IOException e) {
				if (!cancelled.get()) onError.accept(new PoseidonClientException("SSE connection error", e));
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		});
		thread.setDaemon(true);
		thread.start();

		return () -> {
			cancelled.set(true);
			thread.interrupt();
		};
	}

	private boolean isSuccess(int statusCode) {
		return statusCode >= 200 && statusCode < 300;
	}
}