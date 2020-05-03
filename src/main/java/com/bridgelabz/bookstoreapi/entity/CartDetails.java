package com.bridgelabz.bookstoreapi.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

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
	@GenericGenerator(name = "idGen", strategy = "increment")
	@GeneratedValue(generator = "idGen")
	private long cartId;
	
	@Column(name = "placed_timed", nullable = false)
	private LocalDateTime placeTime;
	
	@OneToMany(cascade = CascadeType.ALL, targetEntity = QuantityOfBooks.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "cartId")
	private List<QuantityOfBooks> QuantityOfBooks;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Book> BooksList;
	
	
}
