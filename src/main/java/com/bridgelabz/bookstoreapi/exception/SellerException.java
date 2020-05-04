package com.bridgelabz.bookstoreapi.exception;

public class SellerException extends RuntimeException{

private static final long serialVersionUID = 5460492644658485956L;
	
	private int errorCode;
	private String errorMsg;
	public SellerException(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public SellerException(int errorCode, String errorMsg) {
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

