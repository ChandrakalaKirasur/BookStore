package com.bridgelabz.bookstoreapi.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="order_details")
@Data
@NoArgsConstructor
@ToString
public class OrderDetails {


	@Id
	private long orderId;
	
	@Column(name = "order_placed_time", nullable = false)
	private LocalDateTime orderPlaceTime;
	
	@OneToOne(cascade = CascadeType.ALL, targetEntity = QuantityOfBooks.class)
	@JoinColumn(name = "bookquantity")
	private Long quantityOfBooks;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Book> BooksList;

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public LocalDateTime getOrderPlaceTime() {
		return orderPlaceTime;
	}

	public void setOrderPlaceTime(LocalDateTime orderPlaceTime) {
		this.orderPlaceTime = orderPlaceTime;
	}

	public Long getQuantityOfBooks() {
		return quantityOfBooks;
	}

	public void setQuantityOfBooks(Long quantityOfBooks) {
		this.quantityOfBooks = quantityOfBooks;
	}

	public List<Book> getBooksList() {
		return BooksList;
	}

	public void setBooksList(List<Book> booksList) {
		BooksList = booksList;
	}

	
	
}
