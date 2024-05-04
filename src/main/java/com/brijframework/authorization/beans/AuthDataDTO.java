package com.brijframework.authorization.beans;

public class AuthDataDTO {

	private UserDetailResponse user;
	private String token;

	public UserDetailResponse getUser() {
		return user;
	}

	public void setUser(UserDetailResponse user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
