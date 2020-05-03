package com.bridgelabz.bookstoreapi.response;

public class UserResponse {

	private String Message;
	private String status;
	private Object obj;
	

	public UserResponse(String message, String status) {
		super();
		this.status = status;
		Message = message;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public UserResponse(String message, String status, Object obj) {
		super();
		this.status = status;
		Message = message;
		this.obj = obj;
	}
	
}
