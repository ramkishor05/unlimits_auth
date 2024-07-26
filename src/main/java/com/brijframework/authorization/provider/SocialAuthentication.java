package com.brijframework.authorization.provider;

import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class SocialAuthentication extends UsernamePasswordAuthenticationToken {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SocialAuthentication(Object principal, Object credentials) {
		super(principal, credentials);
	}
	
	public SocialAuthentication(String principal) {
		super(principal,null);
	}

	public SocialAuthentication(String principal, Object credentials, List<GrantedAuthority> grantedAuthority) {
		super(principal, credentials, grantedAuthority);
	}
	
}