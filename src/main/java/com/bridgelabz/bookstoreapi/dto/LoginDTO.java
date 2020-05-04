package com.bridgelabz.bookstoreapi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class LoginDTO {

	
	private String mailOrMobile;
	private String password;
	
	public String getMailOrMobile() {
		return mailOrMobile;
	}
	public void setMailOrMobile(String mailOrMobile) {
		this.mailOrMobile = mailOrMobile;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
