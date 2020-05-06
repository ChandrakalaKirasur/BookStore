package com.bridgelabz.bookstoreapi.entity;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="cart_details")
public class CartDetails {

	
	@Id
	@GenericGenerator(name = "idGen", strategy = "increment")
	@GeneratedValue(generator = "idGen")
	private Long cartId;
	
	@Column(name = "quantity_of_books", nullable = false)
	private Long quantityOfBook;
	
	@OneToOne
	@JsonIgnore
	@JoinTable(name="purchasing_book", joinColumns = @JoinColumn(name="quantityId"),
	inverseJoinColumns = @JoinColumn(name="book_id"))
	private Book book;
	
	public CartDetails() {
	}

	public CartDetails(Long quantity, Book book) {
		this.quantityOfBook = quantity;
		this.book = book;
	}

	public Long getQuantityOfBook() {
		return quantityOfBook;
	}

	public void setQuantityOfBook(Long quantityOfBook) {
		this.quantityOfBook = quantityOfBook;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}
}
