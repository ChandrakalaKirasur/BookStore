package com.bridgelabz.bookstoreapi.dto;

import lombok.Data;

@Data
public class Mail {

	private String to;
	private String subject;
	private String context;
	
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	
	
}
