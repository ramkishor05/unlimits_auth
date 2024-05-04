package com.brijframework.authorization.beans;

public class AuthDataDTO {

	private UIUserDetail user;
	private String token;

	public UIUserDetail getUser() {
		return user;
	}

	public void setUser(UIUserDetail user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
