package ws.mia.poseidon.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Wrapper for a Docker container, containing info useful to Poseidon and related apps
 */
public class PoseidonContainer {

	public enum State {
		RUNNING("running"),
		RESTARTING("restarting"),
		DOWN("created", "removing", "exited", "dead"),
		PAUSED("paused"),
		UNKNOWN();

		private final List<String> dockerStatuses;
  		State(String... dockerStatus) {
			  this.dockerStatuses = List.of(dockerStatus);
		}

		public static State fromDockerStatus(String dockerStatus) {
			  return Arrays.stream(State.values()).filter(state -> state.dockerStatuses.contains(dockerStatus.toLowerCase())).findFirst()
					  .orElse(UNKNOWN);
		}
	}

	private String dockerId;
	private String image;
	private String imageId;
	private List<String> names;
	private int externalPort;
	private Map<String, String> labels;
	private State state;
	private long lastStateUpdate;

	public PoseidonContainer() {
	}

	public String getDockerId() {
		return dockerId;
	}

	public void setDockerId(String dockerId) {
		this.dockerId = dockerId;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public int getExternalPort() {
		return externalPort;
	}

	public void setExternalPort(int externalPort) {
		this.externalPort = externalPort;
	}

	public Map<String, String> getLabels() {
		return labels;
	}

	public void setLabels(Map<String, String> labels) {
		this.labels = labels;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public long getLastStateUpdate() {
		return lastStateUpdate;
	}

	public void setLastStateUpdate(long lastStateUpdate) {
		this.lastStateUpdate = lastStateUpdate;
	}

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	public String getFirstName() {
		if(getNames().isEmpty()) return image;
		return getNames().get(0);
	}
}
