package com.bridgelabz.bookstoreapi.dto;

public class sellerForgetPasswordDto {
	private String password;

	public sellerForgetPasswordDto() {

	}
	public sellerForgetPasswordDto(String password, String confirmpassword) {
		super();
		this.password = password;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "ForgetPasswordDto [password=" + password +  "]";
	}



}