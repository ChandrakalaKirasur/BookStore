package com.bridgelabz.bookstoreapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="Quantity_of_books")
public class QuantityOfBooks {

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	@Id
	@GenericGenerator(name = "idGen", strategy = "increment")
	@GeneratedValue(generator = "idGen")
	private Long quantityId;
	
	@Column(name = "quantity_of_books", nullable = false)
	private Long quantityOfBook;
	
	@OneToOne
	@JoinTable(name="purchasing_book", joinColumns = @JoinColumn(name="quantityId"),
	inverseJoinColumns = @JoinColumn(name="user_id"))
	private Book book;
	
	public QuantityOfBooks() {
	}

	public QuantityOfBooks(Long quantity) {
		this.quantityOfBook = quantity;
	}
	
	public Long getQuantityOfBook() {
		return quantityOfBook;
	}

	public void setQuantityOfBook(Long quantityOfBook) {
		this.quantityOfBook = quantityOfBook;
	}

	
}
