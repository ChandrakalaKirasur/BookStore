package com.bridgelabz.bookstoreapi.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Admin {

	@Id
	@GenericGenerator(name = "idGen", strategy = "increment")
	@GeneratedValue(generator = "idGen")
	private long adminId;
	
	@Column(name = "admin_name", nullable = false)
	private String name;
	
	@Column(name = "admin_email", nullable = false)
	private String email;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "created_time", nullable = false)
	private LocalDateTime createdTime;
	
	@Column(name = "updated_time", nullable = false)
	private LocalDateTime updatedTime;
	
	@Column(name = "admin_phone", nullable = false)
	private long phoneNum;
	
}
