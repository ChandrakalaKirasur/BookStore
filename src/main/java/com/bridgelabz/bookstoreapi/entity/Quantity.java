package com.bridgelabz.bookstoreapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Quantity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long quantityId;
	@Column
	private Long quantityOfBook;
	@Column
    private Double totalprice;

	
	public Quantity() {
		super();
	}
	public Quantity(Long quantityOfBook) {
		super();
		this.quantityOfBook = quantityOfBook;
	}

	public Quantity(Long quantityOfBook, Double totalprice) {
		super();
		this.quantityOfBook = quantityOfBook;
		this.totalprice = totalprice;
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

	public Double getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(Double totalprice) {
		this.totalprice = totalprice;
	}
	
	
}
