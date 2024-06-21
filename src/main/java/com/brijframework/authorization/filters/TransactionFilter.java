package com.brijframework.authorization.filters;

import static com.brijframework.authorization.constant.Constants.AUTHORIZATION;
import static com.brijframework.authorization.constant.Constants.CLIENT_USER_ID;
import static com.brijframework.authorization.constant.Constants.CLIENT_USER_ROLE;
import static com.brijframework.authorization.constant.Constants.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.unlimits.rest.token.TokenUtil;

import com.brijframework.authorization.exceptions.InvalidTokenException;
import com.brijframework.authorization.service.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TransactionFilter extends OncePerRequestFilter { 
	
	private static final Logger log = LoggerFactory.getLogger(TransactionFilter.class);
  
    @Autowired
    private TokenService tokenService; 
  
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException { 
    	log.debug("TokenFilter:: doFilterInternal() -started");
        TransactionRequest requestWrapper = new TransactionRequest(request);
        String authHeader = request.getHeader(AUTHORIZATION); 
        requestWrapper.putHeader("Access-Control-Allow-Origin", "*");
        requestWrapper.putHeader("Access-Control-Allow-Headers", "Content-Type");
        requestWrapper.putHeader("Accept", "*");
        if(StringUtils.isNotEmpty(authHeader) && !authHeader.equalsIgnoreCase("null")) {
        	 String token = authHeader.substring(7);
        	 if (!tokenService.validateToken(token)) { 
             	throw new InvalidTokenException("Invalid token !!");
             }
        	 String username = TokenUtil.getUsername(token); 
        	 String userId = TokenUtil.getUserId(token);
        	 String userRole = TokenUtil.getUserRole(token); 
        	 requestWrapper.setAttribute(CLIENT_USER_ID, userId);
             requestWrapper.putHeader(CLIENT_USER_ID, userId);
             requestWrapper.setAttribute(CLIENT_USER_ROLE, userRole);
             requestWrapper.putHeader(CLIENT_USER_ROLE, userRole);
             requestWrapper.putHeader(CLIENT_TOKEN, token);
             requestWrapper.putHeader(CLIENT_USER_NAME, username);
             if (SecurityContextHolder.getContext().getAuthentication() == null) { 
                 UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null, getGrantedAuthority(userRole)); 
                 authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); 
                 SecurityContextHolder.getContext().setAuthentication(authToken); 
             } 
        }
        filterChain.doFilter(requestWrapper, response); 
        log.debug("TokenFilter:: doFilterInternal() -ended");
    } 
    
    private List<GrantedAuthority> getGrantedAuthority(String authority) {
		return Arrays.asList(new GrantedAuthority() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public String getAuthority() {
				return authority;
			}
		});
	}
} 