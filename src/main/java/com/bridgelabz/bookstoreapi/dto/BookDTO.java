package com.bridgelabz.bookstoreapi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookDTO {
	
	private String bookName;
	
	private String bookAuthor;
	
	private Double bookPrice;
	
	private Long noOfBooks;
	
	private String bookDescription;
	
}
