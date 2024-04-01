package com.brijframwork.authorization.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code =  HttpStatus.FORBIDDEN)
public class InvalidTokenException extends RuntimeException {

	public static final String USER_NOT_EXISTS_IN_SYSTEM = "User not exists in system.";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidTokenException() {
		super(USER_NOT_EXISTS_IN_SYSTEM);
	}

	public InvalidTokenException(String msg) {
		super(msg);
	}

}
