package com.brijframework.authorization.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.brijframework.authorization.filters.TokenAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig { 
	String[] patterns = { 
			"/api/global/authentication/**", 
			"/api/device/authentication/**", 
			"/api/swagger-ui/**", 
			"/v3/api-docs/**",
			"/resources/**"
			};

	private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);
  
    @Autowired
    private TokenAuthenticationFilter tokenAuthenticationFilter; 


    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Bean
 	public WebSecurityCustomizer webSecurityCustomizer() {
 		return (web) -> web.ignoring().requestMatchers(patterns);
 	}
    
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { 
    	log.debug("SecurityConfig :: securityFilterChain() started");
        return http.csrf((csrf)->csrf.disable()).cors(cors->cors.configurationSource(corsConfigurationSource())) 
        .authorizeHttpRequests(authorize->
           authorize.requestMatchers(patterns).permitAll().anyRequest().authenticated()
         ) 
        .sessionManagement(Customizer.withDefaults()) 
        .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .authenticationManager(authenticationManager)
        .build(); 
    } 
  
} 