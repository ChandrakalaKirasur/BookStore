package com.bridgelabz.bookstoreapi.dto;

import io.swagger.annotations.ApiModelProperty;

public class CartdetailsDto {

	@ApiModelProperty(notes = "The Id of the Quantity", required = true)
	private Long quantityId;
	@ApiModelProperty(notes = "The booksId of the Quantity", required = true)
	private Long quantityOfBook;
	@ApiModelProperty(notes = "The TotalPrice of the Book", required = true)
	private Double totalPrice;
	
	
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
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
