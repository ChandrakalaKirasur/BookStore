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
	
}
