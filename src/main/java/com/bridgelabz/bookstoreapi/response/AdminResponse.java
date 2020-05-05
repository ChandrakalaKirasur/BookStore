package com.bridgelabz.bookstoreapi.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class AdminResponse {
	
	private String message;
	private int status;
	private Object resultStatus;
	
	public AdminResponse(String message, int status, Object resultStatus) {
		super();
		this.message = message;
		this.status = status;
		this.resultStatus = resultStatus;
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

	public Object getResultStatus() {
		return resultStatus;
	}

	public void setResultStatus(Object resultStatus) {
		this.resultStatus = resultStatus;
	}
	
	
}
