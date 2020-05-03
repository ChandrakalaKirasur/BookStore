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

	//should be generated using the random class
	@Id
	private long orderId;
	
	@Column(name = "order_placed_time", nullable = false)
	private LocalDateTime orderPlaceTime;
	
	@OneToMany(cascade = CascadeType.ALL, targetEntity = QuantityOfBooks.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "orderId")
	private List<QuantityOfBooks> QuantityOfBooks;
	
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

	public List<QuantityOfBooks> getQuantityOfBooks() {
		return QuantityOfBooks;
	}

	public void setQuantityOfBooks(List<QuantityOfBooks> quantityOfBooks) {
		QuantityOfBooks = quantityOfBooks;
	}

	public List<Book> getBooksList() {
		return BooksList;
	}

	public void setBooksList(List<Book> booksList) {
		BooksList = booksList;
	}

	
	
}
