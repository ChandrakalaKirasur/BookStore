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
@Table(name="user")
@Data
@NoArgsConstructor
@ToString
public class User {

	@Id
	@GenericGenerator(name = "idGen", strategy = "increment")
	@GeneratedValue(generator = "idGen")
	private long userId;
	
	@Column(name = "user_name", nullable = false)
	private String name;
	
	@Column(name = "user_email", nullable = false)
	private String email;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "date", nullable = false)
	private LocalDateTime date;
	
	@Column(name = "verify_status", nullable = false)
	private boolean isVerified;
	
	@Column(name = "user_number", nullable = false)
	private long mobileNum;
	
	@Column(name = "user_profile", nullable = false)
	private String profile;
	
	@OneToMany(cascade = CascadeType.ALL, targetEntity = Address.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private List<Address> address;
	
	@OneToMany(cascade = CascadeType.ALL, targetEntity = ReviewAndRating.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private List<ReviewAndRating> bookRatingAndReview;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<CartDetails> cartBooks;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<WhishListDetails> whilistBooks;

	@ManyToMany(cascade = CascadeType.ALL)
	private List<OrderDetails> orderBookDetails;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	public long getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(long mobileNum) {
		this.mobileNum = mobileNum;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public List<Address> getAddress() {
		return address;
	}

	public void setAddress(List<Address> address) {
		this.address = address;
	}

	public List<ReviewAndRating> getBookRatingAndReview() {
		return bookRatingAndReview;
	}

	public void setBookRatingAndReview(List<ReviewAndRating> bookRatingAndReview) {
		this.bookRatingAndReview = bookRatingAndReview;
	}

	public List<CartDetails> getCartBooks() {
		return cartBooks;
	}

	public void setCartBooks(List<CartDetails> cartBooks) {
		this.cartBooks = cartBooks;
	}

	public List<WhishListDetails> getWhilistBooks() {
		return whilistBooks;
	}

	public void setWhilistBooks(List<WhishListDetails> whilistBooks) {
		this.whilistBooks = whilistBooks;
	}

	public List<OrderDetails> getOrderBookDetails() {
		return orderBookDetails;
	}

	public void setOrderBookDetails(List<OrderDetails> orderBookDetails) {
		this.orderBookDetails = orderBookDetails;
	}
	
	
}
