package com.bridgelabz.bookstoreapi.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
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
}
