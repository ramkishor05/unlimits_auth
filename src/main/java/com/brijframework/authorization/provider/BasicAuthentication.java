package com.brijframework.authorization.provider;

import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class BasicAuthentication extends UsernamePasswordAuthenticationToken {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BasicAuthentication(Object principal, Object credentials) {
		super(principal, credentials);
	}

	public BasicAuthentication(String principal, String password, List<GrantedAuthority> grantedAuthority) {
		super(principal, password, grantedAuthority);
	}
	
}