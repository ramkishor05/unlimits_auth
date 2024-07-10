package com.brijframework.authorization.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;

import com.brijframework.authorization.provider.BasicAuthenticationProvider;
import com.brijframework.authorization.provider.SocialAuthenticationProvider;
import com.brijframework.authorization.provider.TokenAuthenticationProvider;

@Configuration
public class ProviderConfig {

    @Autowired
    private BasicAuthenticationProvider basicAuthenticationProvider;
    
    @Autowired
    private  TokenAuthenticationProvider tokenAuthenticationProvider;
    
    @Autowired
    private  SocialAuthenticationProvider socialAuthenticationProvider;

    @Bean
    public AuthenticationManager authenticationManager() {
      return new ProviderManager(basicAuthenticationProvider, tokenAuthenticationProvider, socialAuthenticationProvider);
    }
}
