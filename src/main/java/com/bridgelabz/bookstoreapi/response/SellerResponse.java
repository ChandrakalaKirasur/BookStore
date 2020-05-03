package com.bridgelabz.bookstoreapi.response;

public class SellerResponse {
	private String Message;
	private int StatusCode;
	private Object obj;

	public SellerResponse(String message, int statusCode, Object obj) {
		Message = message;
		StatusCode = statusCode;
		this.obj = obj;
	}

	public SellerResponse(String message, int statusCode) {
		this.Message = message;
		this.StatusCode = statusCode;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public int getStatusCode() {
		return StatusCode;
	}

	public void setStatusCode(int statusCode) {
		StatusCode = statusCode;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

}
