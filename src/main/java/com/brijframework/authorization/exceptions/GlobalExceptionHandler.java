package com.brijframework.authorization.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
	protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Conflict", 5001, ex.getMessage());

		return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
	
	@ExceptionHandler(value = { UserAlreadyExistsException.class })
	protected ResponseEntity<Object> alreadyExistsException(RuntimeException ex, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT.value(),"Conflict", 1409, ex.getMessage());
		
		return new ResponseEntity<Object>(errorResponse, new HttpHeaders(), HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value = { UserNotFoundException.class})
	protected ResponseEntity<Object> notFoundException(RuntimeException ex, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NO_CONTENT.value(),"NOT_FOUND", 1409, ex.getMessage());
		
		return new ResponseEntity<Object>(errorResponse, new HttpHeaders(), HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(value = { InvalidTokenException.class, BadCredentialsException.class  })
	protected ResponseEntity<Object> invalidException(RuntimeException ex, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN.value(),"NOT_FOUND", 1409, ex.getMessage());
		
		return new ResponseEntity<Object>(errorResponse, new HttpHeaders(), HttpStatus.FORBIDDEN);
	}
}