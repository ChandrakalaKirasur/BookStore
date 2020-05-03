package com.bridgelabz.bookstoreapi.entity;

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
@Table(name="address")
@Data
@NoArgsConstructor
@ToString
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long addressId;
	
	@Column(name = "address", nullable = false)
	private String address;
	
	@Column(name = "house_no", nullable = false)
	private String houseNo;
	
	@Column(name = "street", nullable = false)
	private String street;
	
	@Column(name = "city", nullable = false)
	private String city;
	
	@Column(name = "landmark", nullable = false)
	private String landmark;
	
	@Column(name = "state", nullable = false)
	private String state;
	
	@Column(name = "pincode", nullable = false)
	private String pincode;
	
	@Column(name = "country", nullable = false)
	private String country;
	
}
