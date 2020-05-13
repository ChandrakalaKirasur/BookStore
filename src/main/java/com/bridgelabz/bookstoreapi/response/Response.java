package com.bridgelabz.bookstoreapi.response;

import org.springframework.http.HttpStatus;

public class Response {

	private int status;
	private String msg;
	private Object obj;

	public Response(int status, String msg) {
		this.status = status;
		this.msg = msg;
	}

	public Response(int status, String msg, Object obj) {
		this(status, msg);
		this.obj = obj;
	}
	
	public Response() {
	}

	

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
	
	
}
