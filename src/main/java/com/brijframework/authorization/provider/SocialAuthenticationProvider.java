package com.brijframework.authorization.provider;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.unlimits.rest.crud.beans.Response;

import com.brijframework.authorization.account.entities.EOUserAccount;
import com.brijframework.authorization.account.model.UIUserAccount;
import com.brijframework.authorization.account.model.auth.GlobalLoginRequest;
import com.brijframework.authorization.account.model.auth.GlobalPasswordReset;
import com.brijframework.authorization.account.model.auth.GlobalRegisterRequest;
import com.brijframework.authorization.account.service.UserAccountService;
import com.brijframework.authorization.constant.Authority;


@Component
public class SocialAuthenticationProvider extends DaoAuthenticationProvider {
	
	private static final Logger log = LoggerFactory.getLogger(SocialAuthenticationProvider.class);

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
		log.info("SocialAuthenticationProvider :: authenticate() started");
		this.setPasswordEncoder(passwordEncoder);
		this.setUserDetailsService(userAccountService);
		log.debug("SocialAuthenticationProvider :: authenticate() end");
		return super.authenticate(authentication);
	}
	
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(SocialAuthentication.class);
	}
	
	public UIUserAccount loadUserByUsername(String username, Authority authority) {
		UserAccountService userAccountService = getUserDetailsServiceByRole(authority);
		return userAccountService.loadUserByUsername(username);
	}
	
	public UIUserAccount resetPassword(GlobalPasswordReset passwordReset) {
		return userAccountService.resetPassword(passwordReset);
	}
	
	public UIUserAccount saveOtp(GlobalPasswordReset passwordReset) {
		return userAccountService.saveOtp(passwordReset);
	}

	private UserAccountService getUserDetailsServiceByRole(Authority authority) {
		return userAccountService;
	}

	public Response<Object> register(GlobalRegisterRequest registerRequest) {
		if(registerRequest.getAuthority()==null) {
			registerRequest.setAuthority(Authority.USER);
		}
		Authority authority= registerRequest.getAuthority();
		UserAccountService userDetailsService=getUserDetailsServiceByRole(authority);
		return userDetailsService.register(registerRequest);
	}

	public Response<Object> userLogin(GlobalLoginRequest authRequest) {
		return userAccountService.login(authRequest);
	}

	/**
	 * @param username
	 * @param userRole
	 * @return
	 */
	public Optional<EOUserAccount> find(GlobalLoginRequest authRequest) {
		return userAccountService.find(authRequest);
	}
}