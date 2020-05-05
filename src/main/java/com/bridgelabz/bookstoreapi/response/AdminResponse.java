package com.bridgelabz.bookstoreapi.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminResponse {
	private String message;
	private int status;
	private Object resultStatus;
}
