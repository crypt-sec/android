package org.cryptomator.domain;

import java.io.Serializable;

public class Vault implements Serializable {

	private static final Long NOT_SET = Long.MIN_VALUE;

	public static Builder aVault() {
		return new Builder();
	}

	public static Builder aCopyOf(Vault vault) {
		return new Builder() //
				.withId(vault.getId()) //
				.withCloud(vault.getCloud()) //
				.withCloudType(vault.getCloudType()) //
				.withName(vault.getName()) //
				.withPath(vault.getPath()) //
				.withUnlocked(vault.isUnlocked()) //
				.withSavedPassword(vault.getPassword()) //
				.withVersion(vault.getVersion());
	}

	private final Long id;
	private final String name;
	private final String path;
	private final Cloud cloud;
	private final CloudType cloudType;
	private final boolean unlocked;
	private final String password;
	private final int version;

	private Vault(Builder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.path = builder.path;
		this.cloud = builder.cloud;
		this.unlocked = builder.unlocked;
		this.cloudType = builder.cloudType;
		this.password = builder.password;
		this.version = builder.version;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPath() {
		return path;
	}

	public Cloud getCloud() {
		return cloud;
	}

	public CloudType getCloudType() {
		return cloudType;
	}

	public boolean isUnlocked() {
		return unlocked;
	}

	public String getPassword() {
		return password;
	}

	public int getVersion() {
		return version;
	}

	public static class Builder {

		private Long id = NOT_SET;
		private String name;
		private String path;
		private Cloud cloud;
		private CloudType cloudType;
		private boolean unlocked;
		private String password;
		private int version = -1;

		private Builder() {
		}

		public Builder thatIsNew() {
			this.id = null;
			return this;
		}

		public Builder withId(Long id) {
			if (id < 1) {
				throw new IllegalArgumentException("id must not be smaller one");
			}
			this.id = id;
			return this;
		}

		public Builder withName(String name) {
			this.name = name;
			return this;
		}

		public Builder withPath(String path) {
			this.path = path;
			return this;
		}

		public Builder withUnlocked(boolean unlocked) {
			this.unlocked = unlocked;
			return this;
		}

		public Builder withCloud(Cloud cloud) {
			this.cloud = cloud;

			if (cloud != null) {
				this.cloudType = cloud.type();
			}

			return this;
		}

		public Builder withCloudType(CloudType cloudType) {
			this.cloudType = cloudType;

			if (cloud != null && cloud.type() != cloudType) {
				throw new IllegalStateException("Cloud type must match cloud");
			}

			return this;
		}

		public Builder withNamePathAndCloudFrom(CloudFolder vaultFolder) {
			this.name = vaultFolder.getName();
			this.path = vaultFolder.getPath();
			this.cloud = vaultFolder.getCloud();
			this.cloudType = cloud.type();
			return this;
		}

		public Builder withSavedPassword(String password) {
			this.password = password;
			return this;
		}

		public Builder withVersion(int version) {
			this.version = version;
			return this;
		}

		public Vault build() {
			validate();
			return new Vault(this);
		}

		private void validate() {
			if (NOT_SET.equals(id)) {
				throw new IllegalStateException("id must be set");
			}
			if (name == null) {
				throw new IllegalStateException("name must be set");
			}
			if (path == null) {
				throw new IllegalStateException("path must be set");
			}
			if (cloudType == null) {
				throw new IllegalStateException("cloudtype must be set");
			}
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass())
			return false;
		if (obj == this)
			return true;
		return internalEquals((Vault) obj);
	}

	private boolean internalEquals(Vault obj) {
		return id != null && id.equals(obj.id);
	}

	@Override
	public int hashCode() {
		return id == null ? 0 : id.hashCode();
	}
}
