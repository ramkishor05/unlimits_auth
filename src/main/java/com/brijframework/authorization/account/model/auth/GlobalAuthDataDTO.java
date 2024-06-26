package com.brijframework.authorization.account.model.auth;

import com.brijframework.authorization.account.model.UIUserDetail;

public class GlobalAuthDataDTO {

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
