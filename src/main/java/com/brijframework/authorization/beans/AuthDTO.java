package com.brijframework.authorization.beans;

public class AuthDTO {
	private String success;
	private String message;
	private AuthDataDTO data;

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public AuthDataDTO getData() {
		return data;
	}

	public void setData(AuthDataDTO data) {
		this.data = data;
	}

}
