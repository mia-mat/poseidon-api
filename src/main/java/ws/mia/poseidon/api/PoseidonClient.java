package ws.mia.poseidon.api;

import ws.mia.poseidon.api.model.PoseidonContainer;

import java.util.List;

public interface PoseidonClient {

	List<PoseidonContainer> getContainers();

	String getVersion();

}
