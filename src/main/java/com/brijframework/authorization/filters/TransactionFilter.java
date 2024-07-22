package com.brijframework.authorization.filters;

import static com.brijframework.authorization.constant.Constants.AUTHORIZATION;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.brijframework.authorization.provider.TokenAuthentication;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TransactionFilter extends OncePerRequestFilter {

	private static final Logger log = LoggerFactory.getLogger(TransactionFilter.class);

	private AuthenticationManager authenticationManager;
	
	public TransactionFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager=authenticationManager;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		log.info(getClass().getSimpleName()+":: doFilterInternal -started");
		TransactionRequest requestWrapper = new TransactionRequest(request);
		String authorization = request.getHeader(AUTHORIZATION);
		System.out.println("authorization="+authorization);
		requestWrapper.putHeader("Access-Control-Allow-Origin", "*");
		requestWrapper.putHeader("Access-Control-Allow-Headers", "Content-Type");
		requestWrapper.putHeader("Accept", "*");
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

}