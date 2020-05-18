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

import com.bridgelabz.bookstoreapi.dto.RegisterDto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="user")
@Data
@ToString
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userId;
	
	@Column(name = "user_name", nullable = false)
	private String name;
	
	@Column(name = "email", nullable = false)
	private String email;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "create_time", nullable = false)
	private LocalDateTime createTime;
	
	@Column(name = "update_time")
	private LocalDateTime updateTime;
	
	@Column(name = "verify_status", nullable = false)
	private boolean isVerified;
	
	@Column(name = "user_number", nullable = false)
	private long mobileNum;
	
	@Column(name = "user_profile")
	private String profile;
	
	@OneToMany(cascade = CascadeType.ALL, targetEntity = Address.class)
	@JoinColumn(name = "userId")
	private List<Address> address;
	
	@OneToMany(cascade = CascadeType.ALL, targetEntity = CartDetails.class)
	@JoinColumn(name = "userId")
	private List<CartDetails> cartBooks;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Book> whilistBooks;

	@OneToMany(cascade = CascadeType.ALL, targetEntity = OrderDetails.class)
	@JoinColumn(name = "userId")
	private List<OrderDetails> orderBookDetails;

	public User(RegisterDto register) {
		this.name = register.getName();
		this.email = register.getEmail();
		this.mobileNum = register.getMobile();
		this.password = register.getPassword();
		this.createTime = LocalDateTime.now();
		this.updateTime = LocalDateTime.now();
		this.isVerified = false;
	}
	
	public User() {
		super();
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
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
		return createTime;
	}

	public void setDate(LocalDateTime date) {
		this.createTime = date;
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

	public List<CartDetails> getCartBooks() {
		return cartBooks;
	}

	public void setCartBooks(List<CartDetails> cartBooks) {
		this.cartBooks = cartBooks;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public LocalDateTime getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}

	public List<Book> getWhilistBooks() {
		return whilistBooks;
	}

	public void setWhilistBooks(List<Book> whilistBooks) {
		this.whilistBooks = whilistBooks;
	}

	public List<OrderDetails> getOrderBookDetails() {
		return orderBookDetails;
	}

	public void setOrderBookDetails(List<OrderDetails> orderBookDetails) {
		this.orderBookDetails = orderBookDetails;
	}
	
	
}
