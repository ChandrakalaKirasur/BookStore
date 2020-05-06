package com.bridgelabz.bookstoreapi.exception;

public class AddressException  extends RuntimeException{

private static final long serialVersionUID = 5460492644658485956L;
	
	private int errorCode;
	private String errorMsg;
	
	public AddressException(int errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}
	
	public int getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

}