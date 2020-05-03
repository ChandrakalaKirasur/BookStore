package com.bridgelabz.bookstoreapi.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@ToString
@Table(name="seller")
public class Seller {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long sellerId;
	@Column(name = "seller_name", nullable = false)
	private String sellerName;
	@Column(name = "seller_email", nullable = false)
	private String sellerEmail;
	@Column(name = "seller_mobile_no", nullable = false)
	private String sellerMobileNo;
	@Column(name = "password", nullable = false)
	private String password;
	@Column(name = "created_time", nullable = false)
	private LocalDateTime updated_time;
	@Column(name = "updatedTime", nullable = false)
	private LocalDateTime updatedTime;
	@Column(name = "verificatin_status", nullable = false)
	private boolean verificationStatus;
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
	public String getSellerEmail() {
		return sellerEmail;
	}
	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}
	public String getSellerMobileNo() {
		return sellerMobileNo;
	}
	public void setSellerMobileNo(String sellerMobileNo) {
		this.sellerMobileNo = sellerMobileNo;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public LocalDateTime getUpdated_time() {
		return updated_time;
	}
	public void setUpdated_time(LocalDateTime updated_time) {
		this.updated_time = updated_time;
	}
	public LocalDateTime getUpdatedTime() {
		return updatedTime;
	}
	public void setUpdatedTime(LocalDateTime updatedTime) {
		this.updatedTime = updatedTime;
	}
	public boolean isVerificationStatus() {
		return verificationStatus;
	}
	public void setVerificationStatus(boolean verificationStatus) {
		this.verificationStatus = verificationStatus;
	}
	
	
}
