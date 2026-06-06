package ws.mia.poseidon.api.model;

/**
 * DTO from GitHub Actions or another deployment service to send to Poseidon through its deployment endpoints
 */
public class PoseidonDeploymentPayload {
	private String image;
	private String ref;
	private Repository repository;

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

	public Repository getRepository() {
		return repository;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}

	public static class Repository {
		private String name;
		private String id;
		private Owner owner;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Owner getOwner() {
			return owner;
		}

		public void setOwner(Owner owner) {
			this.owner = owner;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		@Override
		public String toString() {
			return "Repository{" + "name='" + name + '\'' + ", id='" + id + '\'' + ", owner=" + owner + '}';
		}
	}


	public static class Owner {
		private String login;

		public String getLogin() {
			return login;
		}

		public void setLogin(String login) {
			this.login = login;
		}


		@Override
		public String toString() {
			return "Owner{" + "login='" + login + '\'' + '}';
		}
	}


	@Override
	public String toString() {
		return "PoseidonDeploymentPayload{" + "image='" + image + '\'' + ", ref='" + ref + '\'' + ", repository=" + repository + '}';
	}
}
