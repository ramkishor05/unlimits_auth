package com.brijframework.authorization.provider;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import com.brijframework.authorization.account.entities.EOUserAccount;
import com.brijframework.authorization.account.model.UIUserAccount;

public class TokenAuthentication extends PreAuthenticatedAuthenticationToken  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UIUserAccount userDetails;
	private EOUserAccount userAccount;
	private final String token;
	private Collection<GrantedAuthority> grantedAuthority;


	public TokenAuthentication(String token) {
		super(token, null);
		this.token = filteredToken(token);
		this.setAuthenticated(true);
	}

	public TokenAuthentication(String token, UIUserAccount userDetails) {
		super(token, null);
		this.token = filteredToken(token);
		this.userDetails = userDetails;
		this.setAuthenticated(true);
	}

	public TokenAuthentication(String token, UIUserAccount userDetails, List<GrantedAuthority> grantedAuthority) {
		super(token, null);
		this.token = filteredToken(token);
		this.userDetails = userDetails;
		this.setAuthenticated(true);
	}

	private String filteredToken(String token) {
		if(token==null) {
			return token;
		}
		return token.startsWith("\"") && token.endsWith("\"")? token.substring(1, token.length()-1): token;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
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
	public String getName() {
		return token;
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