package com.brijframework.authorization.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code =  HttpStatus.NON_AUTHORITATIVE_INFORMATION)
public class UnauthorizedAccessException extends RuntimeException {

	public static final String Unauthorized_Access_Msg = "Unauthorized Access!!";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UnauthorizedAccessException() {
		super(Unauthorized_Access_Msg);
	}

	public UnauthorizedAccessException(String msg) {
		super(msg);
	}

}
