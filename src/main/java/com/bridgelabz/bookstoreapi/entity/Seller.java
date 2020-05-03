package com.bridgelabz.bookstoreapi.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.bridgelabz.bookstoreapi.dto.RegisterDto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "seller")
@Data
@NoArgsConstructor
@ToString
public class Seller {
	
	@Id
	@GenericGenerator(name = "idGen", strategy = "increment")
	@GeneratedValue(generator = "idGen")
	@Column(name = "seller_id")
	private Long sellerId;

	@Column(name = "seller_name", nullable = false)
	private String sellerName;

	@Column(name = "email_address", nullable = false, unique = true)
	private String emailAddress;

	@Column(name = "mobile", nullable = false, unique = true)
	private Long mobile;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "verification_status", nullable = false, columnDefinition = "bit(1) default 0")
	private boolean verificationStatus;

	@Column(name = "created_time", nullable = false)
	private LocalDateTime createdtTime;

	@Column(name = "updated_time", nullable = false)
	private LocalDateTime updatedTime;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "seller_id")
	private List<Book> sellerBooks;
	
	public Seller(RegisterDto register) {
		this.sellerName = register.getName();
		this.emailAddress = register.getEmailAddress();
		this.mobile = register.getMobile();
		this.password = register.getPassword();
		this.createdtTime = LocalDateTime.now();
		this.updatedTime = LocalDateTime.now();
		this.verificationStatus = false;
	}
}
