package com.bridgelabz.bookstoreapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.bridgelabz.bookstoreapi.dto.AddressDto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "address")
@Data
@ToString
public class Address {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long addressId;
	
	@Column(name = "address", nullable = false)
	private String address;
	
	@Column(name = "city", nullable = false)
	private String city;
	
	@Column(name = "landmark", nullable = false)
	private String landmark;
	
	@Column(name = "locality", nullable = false)
	private String locality;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "phoneNumber", nullable = false)
	private String phoneNumber;
	
//	@Column(name = "state", nullable = false)
//	private String state;
	@Column(name = "pincode", nullable = false)
	private String pincode;
//	@Column(name = "country", nullable = false)
//	private String country;
	@Column(name = "type", nullable = false)
	private String type;
	
	
	public Address() {
		super();
	}

	public Address(AddressDto address2) {
		this.address=address2.getAddress();
		this.city=address2.getCity();
		this.landmark=address2.getLandmark();
		this.locality=address2.getName();
		this.name=address2.getName();
		this.phoneNumber=address2.getPhoneNumber();
		this.pincode=address2.getPincode();
		this.type=address2.getType();
	
	}

	public String getAddress() {
		return address;
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	
	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
