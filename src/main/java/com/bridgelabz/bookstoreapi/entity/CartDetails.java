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
@Table(name="cart_details")
@Data
@NoArgsConstructor
@ToString
public class CartDetails {

	
	@Id
	@Column(name = "placed_timed", nullable = false)
	private LocalDateTime placeTime;
	
	@OneToOne(cascade = CascadeType.ALL, targetEntity = QuantityOfBooks.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "cartId")
	private List<QuantityOfBooks> QuantityOfBooks;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Book> BooksList;


	public LocalDateTime getPlaceTime() {
		return placeTime;
	}

	public void setPlaceTime(LocalDateTime placeTime) {
		this.placeTime = placeTime;
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
