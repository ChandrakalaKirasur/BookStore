package com.bridgelabz.bookstoreapi.dto;

import io.swagger.annotations.ApiModelProperty;

public class CartdetailsDto {

	@ApiModelProperty(notes = "The Id of the Quantity", required = true)
	private Long quantityId;
	@ApiModelProperty(notes = "The booksId of the Quantity", required = true)
	private Long quantityOfBook;
	
	public Long getQuantityId() {
		return quantityId;
	}
	public void setQuantityId(Long quantityId) {
		this.quantityId = quantityId;
	}
	public Long getQuantityOfBook() {
		return quantityOfBook;
	}
	public void setQuantityOfBook(Long quantityOfBook) {
		this.quantityOfBook = quantityOfBook;
	}
	
	
}
