package ws.mia.poseidon.api.model;

public class PoseidonDeploymentPayload {

	private String image;
	private String ref;
	private String branch;

	private String repository;
	private String repositoryId;

	private String repositoryOwner;
	private String repositoryOwnerId;

	private String repositoryName;
	private String repositoryUrl;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getRepository() {
		return repository;
	}

	public void setRepository(String repository) {
		this.repository = repository;
	}

	public String getRepositoryId() {
		return repositoryId;
	}

	public void setRepositoryId(String repositoryId) {
		this.repositoryId = repositoryId;
	}

	public String getRepositoryOwner() {
		return repositoryOwner;
	}

	public void setRepositoryOwner(String repositoryOwner) {
		this.repositoryOwner = repositoryOwner;
	}

	public String getRepositoryOwnerId() {
		return repositoryOwnerId;
	}

	public void setRepositoryOwnerId(String repositoryOwnerId) {
		this.repositoryOwnerId = repositoryOwnerId;
	}

	public String getRepositoryName() {
		return repositoryName;
	}

	public void setRepositoryName(String repositoryName) {
		this.repositoryName = repositoryName;
	}

	public String getRepositoryUrl() {
		return repositoryUrl;
	}

	public void setRepositoryUrl(String repositoryUrl) {
		this.repositoryUrl = repositoryUrl;
	}

	@Override
	public String toString() {
		return "PoseidonDeploymentPayload{" +
				"image='" + image + '\'' +
				", ref='" + ref + '\'' +
				", branch='" + branch + '\'' +
				", repository='" + repository + '\'' +
				", repositoryId='" + repositoryId + '\'' +
				", repositoryOwner='" + repositoryOwner + '\'' +
				", repositoryOwnerId='" + repositoryOwnerId + '\'' +
				", repositoryName='" + repositoryName + '\'' +
				", repositoryUrl='" + repositoryUrl + '\'' +
				'}';
	}
}