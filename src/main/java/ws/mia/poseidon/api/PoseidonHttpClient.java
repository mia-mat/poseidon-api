package ws.mia.poseidon.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ws.mia.poseidon.api.model.PoseidonContainer;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

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

	private boolean isSuccess(int statusCode) {
		return statusCode >= 200 && statusCode < 300;
	}
}
