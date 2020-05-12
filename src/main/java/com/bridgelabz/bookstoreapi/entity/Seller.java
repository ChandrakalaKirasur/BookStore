package com.bridgelabz.bookstoreapi.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@ToString
public class Seller {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "seller_id")
	private Long sellerId;

	@Column(name = "seller_name", nullable = false)
	private String sellerName;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

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
		this.email = register.getEmail();
		this.mobile = register.getMobile();
		this.password = register.getPassword();
		this.createdtTime = LocalDateTime.now();
		this.updatedTime = LocalDateTime.now();
		this.verificationStatus = false;
	}
	public Seller()
	{
		
	}
	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getMobile() {
		return mobile;
	}

	public void setMobile(Long mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isVerificationStatus() {
		return verificationStatus;
	}

	public void setVerificationStatus(boolean verificationStatus) {
		this.verificationStatus = verificationStatus;
	}

	public LocalDateTime getCreatedtTime() {
		return createdtTime;
	}

	public void setCreatedtTime(LocalDateTime createdtTime) {
		this.createdtTime = createdtTime;
	}

	public LocalDateTime getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(LocalDateTime updatedTime) {
		this.updatedTime = updatedTime;
	}

	public List<Book> getSellerBooks() {
		return sellerBooks;
	}

	public void setSellerBooks(List<Book> sellerBooks) {
		this.sellerBooks = sellerBooks;
	}
	
	
}
