package com.brijframework.authorization.filters;

import static com.brijframework.authorization.constant.Constants.AUTHORIZATION;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.brijframework.authorization.provider.TokenAuthentication;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Order(0)
public class TokenAuthenticationFilter extends OncePerRequestFilter {
	
	/**
	 * 
	 */
	private static final Logger log = LoggerFactory.getLogger(TokenAuthenticationFilter.class);

	private AuthenticationManager authenticationManager;
	
	public TokenAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager=authenticationManager;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		log.info(getClass().getSimpleName()+":: doFilterInternal -started");
		TransactionRequest requestWrapper = new TransactionRequest(request);
		String authorization = request.getHeader(AUTHORIZATION);
		System.out.println("url : "+request.getRequestURI());
		System.out.println("authorization="+authorization);
		SecurityContext context = SecurityContextHolder.getContext();
		if(context.getAuthentication()!=null && context.getAuthentication().isAuthenticated()) {
		    log.info(getClass().getSimpleName()+" :: doFilterInternal - Authorization in request");
			filterChain.doFilter(requestWrapper, response);
		    return;
		}
		if(StringUtils.isEmpty(authorization) || authorization.equalsIgnoreCase("null")) {
	      log.info(getClass().getSimpleName()+" :: doFilterInternal - Did not authorization in request");
	      filterChain.doFilter(requestWrapper, response);
	      return;
		}
		TokenAuthentication tokenAuthentication = new TokenAuthentication(authorization);
		SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(tokenAuthentication));
		filterChain.doFilter(requestWrapper, response);
		log.info(getClass().getSimpleName()+":: doFilterInternal -ended");
	}
	
	public static void main(String[] args) {
		
	}

}