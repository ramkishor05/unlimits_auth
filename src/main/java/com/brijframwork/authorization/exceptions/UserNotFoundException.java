package com.brijframwork.authorization.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code =  HttpStatus.FORBIDDEN)
public class UserNotFoundException extends RuntimeException {

	public static final String USER_NOT_EXISTS_IN_SYSTEM = "User not exists in system.";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UserNotFoundException() {
		super(USER_NOT_EXISTS_IN_SYSTEM);
	}

	public UserNotFoundException(String msg) {
		super(msg);
	}

}
