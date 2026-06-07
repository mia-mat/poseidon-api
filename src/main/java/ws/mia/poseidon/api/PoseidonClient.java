package ws.mia.poseidon.api;

import ws.mia.poseidon.api.model.PoseidonContainer;
import ws.mia.poseidon.api.model.PoseidonContainerEvent;

import java.util.List;
import java.util.function.Consumer;

public interface PoseidonClient {

	List<PoseidonContainer> getContainers();

	String getVersion();

	/**
	 * Opens an SSE connection to the container event stream.
	 * The provided consumer is called for each event received.
	 * Returns a Runnable that closes the connection when called.
	 */
	Runnable subscribeToEvents(Consumer<PoseidonContainerEvent> onEvent, Consumer<Throwable> onError);

}