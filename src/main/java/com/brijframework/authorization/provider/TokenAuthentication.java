package com.brijframework.authorization.provider;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.brijframework.authorization.account.entities.EOUserAccount;
import com.brijframework.authorization.account.model.UIUserAccount;

public class TokenAuthentication implements Authentication {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean isAuthenticated;
	private UIUserAccount userDetails;
	private EOUserAccount userAccount;
	private final String token;
	private Collection<? extends GrantedAuthority> grantedAuthority;

	public TokenAuthentication(String token) {
		this.token = token;
		this.isAuthenticated = true;
	}

	public TokenAuthentication(String token, UIUserAccount userDetails) {
		this.token = token;
		this.userDetails = userDetails;
		this.isAuthenticated = true;
	}

	public TokenAuthentication(String token, UIUserAccount userDetails, List<GrantedAuthority> grantedAuthority) {
		this.token = token;
		this.userDetails = userDetails;
		this.isAuthenticated = true;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return grantedAuthority;
	}

	@Override
	public Object getCredentials() {
		return token;
	}

	@Override
	public Object getDetails() {
		return userDetails;
	}

	@Override
	public Object getPrincipal() {
		return userDetails;
	}

	@Override
	public boolean isAuthenticated() {
		return isAuthenticated;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		this.isAuthenticated=isAuthenticated;
	}

	@Override
	public String getName() {
		return userDetails.getUsername();
	}

	public UIUserAccount getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UIUserAccount userDetails) {
		this.userDetails = userDetails;
	}

	public EOUserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(EOUserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public String getToken() {
		return token;
	}
}