package com.brijframework.authorization.exceptions;
public class ErrorResponse {
	private final int statusCode;
	private final String statusMessage;
    private final int errorCode;
    private final String errorMessage;

    public ErrorResponse(int statusCode, String statusMessage,int errorCode, String errorMessage) {
        this.statusCode = statusCode;
		this.statusMessage = statusMessage;
		this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
    
    public int getStatusCode() {
		return statusCode;
	}



	public String getStatusMessage() {
		return statusMessage;
	}



	public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}