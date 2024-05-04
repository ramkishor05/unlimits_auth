package com.brijframework.authorization.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.brijframework.authorization.filters.TokenFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig { 
	String[] patterns = { 
			"/api/auth/**", 
			"/api/user/role/**", 
			"/api/user/detail/**", 
			"/api/menu/group/**", 
			"/api/menu/item/**", 
			"/api/role/menu/group/**", 
			"/api/role/menu/item/**", 
			"/api/swagger-ui/**", 
			"/v3/api-docs/**" 
			};

	private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);
  
    @Autowired
    private TokenFilter authFilter; 
    
    @Autowired
    private AuthenticationProvider authenticationProvider;
      
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { 
    	log.debug("SecurityConfig :: securityFilterChain() started");
        return http.csrf((csrf)->csrf.disable()).cors(cors->cors.disable()) 
                .authorizeHttpRequests(authorize->authorize.requestMatchers(patterns).permitAll().anyRequest()
                        .authenticated()) 
                .sessionManagement(Customizer.withDefaults()) 
                .authenticationProvider(authenticationProvider) 
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class) 
                .build(); 
    } 
  
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception { 
        return config.getAuthenticationManager(); 
    } 
} 