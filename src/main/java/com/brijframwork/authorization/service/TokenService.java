package com.brijframwork.authorization.service;

public interface TokenService {

	String extractUsername(String token);

	String extractRole(String token);

	Boolean validateToken(String token, String username);

	String generateToken(String username, String string);
	
}
