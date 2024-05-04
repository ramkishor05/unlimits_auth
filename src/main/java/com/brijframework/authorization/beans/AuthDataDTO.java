package com.brijframwork.authorization.beans;

public class AuthDataDTO {

	private UIUserAccount user;
	private String token;

	public UIUserAccount getUser() {
		return user;
	}

	public void setUser(UIUserAccount user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
