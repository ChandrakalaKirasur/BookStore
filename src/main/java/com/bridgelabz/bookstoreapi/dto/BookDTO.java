package com.bridgelabz.bookstoreapi.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookDTO {
	
	@ApiModelProperty(notes = "The name of the book", required = true)
	private String bookName;
	
	@ApiModelProperty(notes = "The authot-name of the book", required = true)
	private String bookAuthor;
	
	@ApiModelProperty(notes = "The price of the book", required = true)
	private Double bookPrice;
	
	@ApiModelProperty(notes = "The number of the book", required = true)
	private Long noOfBooks;
	
	@ApiModelProperty(notes = "The description of the book", required = true)
	private String bookDescription;

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getBookAuthor() {
		return bookAuthor;
	}

	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}

	public Double getBookPrice() {
		return bookPrice;
	}

	public void setBookPrice(Double bookPrice) {
		this.bookPrice = bookPrice;
	}

	public Long getNoOfBooks() {
		return noOfBooks;
	}

	public void setNoOfBooks(Long noOfBooks) {
		this.noOfBooks = noOfBooks;
	}

	public String getBookDescription() {
		return bookDescription;
	}

	public void setBookDescription(String bookDescription) {
		this.bookDescription = bookDescription;
	}
	
	
}
