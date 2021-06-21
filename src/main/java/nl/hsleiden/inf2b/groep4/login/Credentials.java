package nl.hsleiden.inf2b.groep4.login;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Credentials {
	@JsonProperty
	private String username;
	@JsonProperty
	private String password;

	public Credentials() {
	}

	public Credentials(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
