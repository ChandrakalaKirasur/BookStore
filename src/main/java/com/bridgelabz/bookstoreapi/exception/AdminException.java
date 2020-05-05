package com.bridgelabz.bookstoreapi.exception;
import org.springframework.http.HttpStatus;

public class AdminException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	private int errorCode;
	private String errorMessage;
	
	public  AdminException(int statusCode, String message) {
		super();
		this.errorCode=statusCode;
		this.errorMessage=message;
	}

	

	public int getErrorCode() {
		return errorCode;
	}



	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}



	public String getErrorMessage() {
		return errorMessage;
	}



	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

		
}
