package com.brijframework.authorization.adptor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.brijframework.authorization.beans.AuthDTO;
import com.brijframework.authorization.beans.PasswordReset;
import com.brijframework.authorization.beans.UIUserAccount;
import com.brijframework.authorization.constant.Authority;
import com.brijframework.authorization.service.UserAccountService;


@Component
public class AuthProvider extends DaoAuthenticationProvider {
	
	private static final Logger log = LoggerFactory.getLogger(AuthProvider.class);

	@Autowired
	//@Qualifier(PATIENT_USER_SERVICE)
	private UserAccountService userAccountService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	protected void doAfterPropertiesSet() {
		
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		log.debug("AuthProvider :: authenticate() started");
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		List<String> authorityList=authorities==null ? new ArrayList<>(): 
			authorities.stream().map(authoritie -> authoritie.getAuthority()).collect(Collectors.toList());
		UserAccountService userDetailsService=null; 
		if(authorityList.contains(Authority.ADMIN.toString())) {
			userDetailsService=userAccountService;
		}
		if(authorityList.contains(Authority.DEVELOPER.toString())) {
			userDetailsService=userAccountService;
		}
		if(authorityList.contains(Authority.USER.toString())) {
			userDetailsService=userAccountService;
		}
		this.setPasswordEncoder(passwordEncoder);
		this.setUserDetailsService(userDetailsService);
		log.debug("AuthProvider :: authenticate() end");
		Authentication authenticate = super.authenticate(authentication);
		List<String> list = authenticate.getAuthorities().stream().map(authoritie->authoritie.getAuthority()).toList();
		for(GrantedAuthority authority : authentication.getAuthorities()) {
			if(!list.contains(authority.getAuthority())) {
				throw new BadCredentialsException(this.messages
						.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
			}
		}
		return authenticate;
	}
	
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		super.additionalAuthenticationChecks(userDetails, authentication);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
	public UIUserAccount loadUserByUsername(String username, String authority) {
		UserAccountService userDetailsService=null; 
		if(authority.equalsIgnoreCase(Authority.ADMIN.toString())) {
			userDetailsService=userAccountService;
		}
		if(authority.equalsIgnoreCase(Authority.DEVELOPER.toString())) {
			userDetailsService=userAccountService;
		}
		if(authority.equalsIgnoreCase(Authority.USER.toString())) {
			userDetailsService=userAccountService;
		}
		return userDetailsService.loadUserByUsername(username);
	}
	
	public UIUserAccount resetPassword(PasswordReset passwordReset) {
		UserAccountService userDetailsService= getUserDetailsServiceByRole(passwordReset);
		return userDetailsService.resetPassword(passwordReset);
	}
	
	public UIUserAccount saveOtp(PasswordReset passwordReset) {
		UserAccountService userDetailsService= getUserDetailsServiceByRole(passwordReset);
		return userDetailsService.saveOtp(passwordReset);
	}

	private UserAccountService getUserDetailsServiceByRole(PasswordReset passwordReset) {
		if(passwordReset.getAuthority().equals(Authority.ADMIN)) {
			return userAccountService;
		}
		if(passwordReset.getAuthority().equals(Authority.DEVELOPER)) {
			return userAccountService;
		}
		if(passwordReset.getAuthority().equals(Authority.USER)) {
			return userAccountService;
		}
		return null;
	}

	public AuthDTO register(UIUserAccount userAccount , Authority authority) {
		UserAccountService userDetailsService=null; 
		if(authority.equals(Authority.ADMIN)) {
			userDetailsService=userAccountService;
		}
		if(authority.equals(Authority.DEVELOPER)) {
			userDetailsService=userAccountService;
		}
		if(authority.equals(Authority.USER)) {
			userDetailsService=userAccountService;
		}
		return userDetailsService.register(userAccount, authority);
	}
}