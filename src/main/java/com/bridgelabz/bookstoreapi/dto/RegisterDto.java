package com.bridgelabz.bookstoreapi.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class RegisterDto {

	@NotNull(message = "Name field can't be empty!!!")
	@Pattern(regexp = "^[a-zA-Z]*", message = "Digits and Special characters are not allowed Frist Name field")
	@Size(min = 3)
	private String name;

	@NotNull(message = "Email address field can't be empty!!!")
	@Email(message = "Enter valid mail address!!!")
	private String email;

	@NotNull(message = "Moblie number field can't be empty!!!")
	private Long mobile;

	@NotNull(message = "Password field can't be empty!!!")
	private String password;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String emailAddress) {
		this.email = emailAddress;
	}

	public Long getMobile() {
		return mobile;
	}

	public void setMobile(Long mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
