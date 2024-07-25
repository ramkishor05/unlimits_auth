package com.brijframework.authorization.account.model.auth;

import java.io.Serializable;

import com.brijframework.authorization.constant.Authority;

public class GlobalRegisterRequest extends GlobalLoginRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String registeredPhone;
	private String registeredEmail;
	private String accountName;
	private Authority authority;
	
	public String getRegisteredPhone() {
		return registeredPhone;
	}

	public void setRegisteredPhone(String registeredPhone) {
		this.registeredPhone = registeredPhone;
	}

	public String getRegisteredEmail() {
		return registeredEmail;
	}

	public void setRegisteredEmail(String registeredEmail) {
		this.registeredEmail = registeredEmail;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Authority getAuthority() {
		if(authority==null) {
			authority= Authority.USER;
		}
		return authority;
	}

	public void setAuthority(Authority authority) {
		this.authority = authority;
	}

}
