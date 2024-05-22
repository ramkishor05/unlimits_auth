package com.brijframework.authorization.service;

import java.util.Date;

public interface TokenService {

	String extractUsername(String token);

	String extractRole(String token);

	Boolean validateToken(String token);

	String generateToken(String username, Long userId,  String role);

	String changeExpiration(String token, Date expiration);

	Date buildExprireationDate();

	String login(String username,Long userId , String string);

	String logout(String token);

	Object getUserDetailFromToken(String token);
	
}
