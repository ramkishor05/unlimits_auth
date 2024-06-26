package com.brijframework.authorization.device.account.model;

import com.brijframework.authorization.account.model.UIUserDetail;

public class DeviceAuthDataDTO {

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
