package com.brijframework.authorization.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
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
import org.unlimits.rest.context.ApiSecurityContext;
import org.unlimits.rest.crud.beans.Response;

import com.brijframework.authorization.account.entities.EOUserAccount;
import com.brijframework.authorization.account.model.UIUserAccount;
import com.brijframework.authorization.account.model.auth.GlobalLoginRequest;
import com.brijframework.authorization.account.model.auth.GlobalPasswordReset;
import com.brijframework.authorization.account.model.auth.GlobalRegisterRequest;
import com.brijframework.authorization.account.service.UserAccountService;
import com.brijframework.authorization.constant.Authority;
import com.brijframework.authorization.exceptions.UnauthorizedAccessException;


@Component
public class BasicAuthenticationProvider extends DaoAuthenticationProvider {
	
	private static final Logger log = LoggerFactory.getLogger(BasicAuthenticationProvider.class);

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
		System.out.println("authentication="+authentication);
		log.info("BasicAuthenticationProvider :: authenticate() started");
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
		log.info("BasicAuthenticationProvider :: authenticate() end");
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
		return authentication.equals(BasicAuthentication.class);
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
	
	public UIUserAccount resetPassword(GlobalPasswordReset passwordReset) {
		UserAccountService userDetailsService= getUserDetailsServiceByRole(passwordReset);
		return userDetailsService.resetPassword(passwordReset);
	}
	
	public UIUserAccount saveOtp(GlobalPasswordReset passwordReset) {
		UserAccountService userDetailsService= getUserDetailsServiceByRole(passwordReset);
		return userDetailsService.saveOtp(passwordReset);
	}

	private UserAccountService getUserDetailsServiceByRole(GlobalPasswordReset passwordReset) {
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

	public Response register(GlobalRegisterRequest registerRequest) {
		if(registerRequest.getAuthority()==null) {
			registerRequest.setAuthority(Authority.USER);
		}
		Authority authority= registerRequest.getAuthority();
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
		return userDetailsService.register(registerRequest);
	}

	public Response userLogin(GlobalLoginRequest authRequest) {
		if(authRequest.getAuthority()==null) {
			authRequest.setAuthority(Authority.USER);
		}
		Authority authority= authRequest.getAuthority();
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
		return userDetailsService.login(authRequest);
	}

	/**
	 * @param username
	 * @param userRole
	 * @return
	 */
	public Optional<EOUserAccount> find(GlobalLoginRequest authRequest) {
		if(authRequest.getAuthority()==null) {
			authRequest.setAuthority(Authority.USER);
		}
		Authority authority= authRequest.getAuthority();
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
		return userDetailsService.find(authRequest);
	}

	public UIUserAccount getUserDetail() {
		EOUserAccount currentAccount = (EOUserAccount) ApiSecurityContext.getContext().getCurrentAccount();
		if(currentAccount==null) {
			throw new UnauthorizedAccessException();
		}
		return userAccountService.getUserDetail(currentAccount);
	}
}