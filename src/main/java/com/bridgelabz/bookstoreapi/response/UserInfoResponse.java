package com.bridgelabz.bookstoreapi.response;

public class UserInfoResponse {
	private String message;
	private int status;
	private Object obj;

	public UserInfoResponse(String message, int status, Object obj) {
		this.message = message;
		this.status = status;
		this.obj = obj;
	}

	public UserInfoResponse(String message, int status) {
		this.message = message;
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
}