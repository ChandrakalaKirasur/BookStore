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
	
	
}
