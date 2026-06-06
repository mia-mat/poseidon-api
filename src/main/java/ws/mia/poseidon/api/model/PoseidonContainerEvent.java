package ws.mia.poseidon.api.model;

// for SSE observers
public class PoseidonContainerEvent {

	private final String eventName;
	private final PoseidonContainer container;
	private final long timestamp;

	public PoseidonContainerEvent(String eventName, PoseidonContainer container) {
		this.eventName = eventName;
		this.container = container;
		this.timestamp = System.currentTimeMillis();
	}

	public String getEventName() {
		return eventName;
	}

	public PoseidonContainer getContainer() {
		return container;
	}

	public long getTimestamp() {
		return timestamp;
	}
}
