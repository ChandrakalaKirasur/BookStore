package com.bridgelabz.bookstoreapi.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDto {
	private String name;
	private String email;
	private String password;
	private LocalDateTime createdTime;
	private LocalDateTime updatedTime;
	private long phoneNum;
}
