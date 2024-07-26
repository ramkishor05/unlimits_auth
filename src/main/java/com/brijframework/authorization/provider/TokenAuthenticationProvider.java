package com.brijframework.authorization.provider;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.unlimits.rest.context.ApiSecurityContext;
import org.unlimits.rest.context.ApiTokenContext;

import com.brijframework.authorization.account.entities.EOUserToken;
import com.brijframework.authorization.account.repository.UserTokenRepository;
import com.brijframework.authorization.constant.Constants;

@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {

	private static final Logger log = LoggerFactory.getLogger(TokenAuthenticationProvider.class);

	@Autowired
	private UserTokenRepository userTokenRepository;
	
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		log.info("TokenAuthenticationProvider :: authenticate() started");
		if(authentication instanceof TokenAuthentication) {
			TokenAuthentication tokenAuthentication= (TokenAuthentication) authentication; 
			String token = tokenAuthentication.getToken().startsWith(Constants.TOKEN_PREFIX) ?  tokenAuthentication.getToken().substring(7) : tokenAuthentication.getToken();
			Optional<EOUserToken> findBySource = userTokenRepository.findBySource(token);
			if(findBySource.isPresent()) {
				EOUserToken eoUserToken = findBySource.get();
				if( ApiTokenContext.validateToken(eoUserToken.getTarget())) {
					ApiSecurityContext.getContext().setCurrentAccount(eoUserToken.getUserAccount());
				}else {
					log.info("Token validation failed : "+token);
					tokenAuthentication.setAuthenticated(false);
				}
			}else {
				log.info("Token not matched : "+token);
				tokenAuthentication.setAuthenticated(false);
			}
		}
		authentication.setAuthenticated(true);
		log.info("TokenAuthenticationProvider :: authenticate() ended");
		return authentication;
	}
	
	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(TokenAuthentication.class);
	}
}
