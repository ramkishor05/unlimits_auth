package com.brijframwork.authorization.filters;

import static com.brijframwork.authorization.constant.Constants.AUTHORIZATION;
import static com.brijframwork.authorization.constant.Constants.BEARER;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.brijframwork.authorization.service.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TokenFilter extends OncePerRequestFilter { 
	
	private static final Logger log = LoggerFactory.getLogger(TokenFilter.class);
  
    @Autowired
    private TokenService tokenService; 
  
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException { 
    	log.debug("TokenFilter:: doFilterInternal() -started");
        String authHeader = request.getHeader(AUTHORIZATION); 
        String token = null; 
        String username = null; 
        String role = null; 
        if (authHeader != null && authHeader.startsWith(BEARER)) { 
            token = authHeader.substring(7);
            username = tokenService.extractUsername(token); 
            role =tokenService.extractRole(token);
            log.debug("extracted user role {} ::",role);
        } 
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) { 
            if (tokenService.validateToken(token, username)) { 
            	log.debug("valid token");
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null, getGrantedAuthority(role)); 
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); 
                SecurityContextHolder.getContext().setAuthentication(authToken); 
            } 
        } 
        filterChain.doFilter(request, response); 
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