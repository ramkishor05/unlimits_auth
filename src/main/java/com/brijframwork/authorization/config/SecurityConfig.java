package com.brijframwork.authorization.config;

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

import com.brijframwork.authorization.filters.TokenFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig { 
	
	private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);
  
    @Autowired
    private TokenFilter authFilter; 
    
    @Autowired
    private AuthenticationProvider authenticationProvider;
      
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { 
    	log.debug("SecurityConfig :: securityFilterChain() started");
        return http.csrf((csrf)->csrf.disable()).cors(cors->cors.disable()) 
                .authorizeHttpRequests(authorize->authorize.requestMatchers("/api/auth/**").permitAll().anyRequest()
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