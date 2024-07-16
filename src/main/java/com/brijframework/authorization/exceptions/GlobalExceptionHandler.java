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

	@ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class, Exception.class, RuntimeException.class })
	protected ResponseEntity<Object> handleConflict(Exception ex, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Server", 5001, ex.getMessage());

		return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
	
	@ExceptionHandler(value = { UserAlreadyExistsException.class })
	protected ResponseEntity<Object> alreadyExistsException(UserAlreadyExistsException ex, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT.value(),"Conflict", 1409, ex.getMessage());
		
		return new ResponseEntity<Object>(errorResponse, new HttpHeaders(), HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value = { UserNotFoundException.class})
	protected ResponseEntity<Object> notFoundException(UserNotFoundException ex, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(),"NOT_FOUND", 1404, ex.getMessage());
		
		return new ResponseEntity<Object>(errorResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = { InvalidTokenException.class})
	protected ResponseEntity<Object> invalidException(InvalidTokenException ex, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(),"UNAUTHORIZED", 1401, ex.getMessage());

		return new ResponseEntity<Object>(errorResponse, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(value = { BadCredentialsException.class  })
	protected ResponseEntity<Object> invalidException(BadCredentialsException ex, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN.value(),"FORBIDDEN", 1403, ex.getMessage());
		
		return new ResponseEntity<Object>(errorResponse, new HttpHeaders(), HttpStatus.FORBIDDEN);
	}
}