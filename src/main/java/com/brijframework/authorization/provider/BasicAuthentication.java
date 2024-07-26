package com.brijframework.authorization.provider;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class BasicAuthentication extends UsernamePasswordAuthenticationToken {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BasicAuthentication(String principal, String credentials) {
		this(principal, credentials, getGrantedAuthority());
	}

	private static List<GrantedAuthority> getGrantedAuthority() {
		return Arrays.asList();
	}

	public BasicAuthentication(String principal, String password, List<GrantedAuthority> grantedAuthority) {
		super(principal, password, grantedAuthority);
	}
	
}