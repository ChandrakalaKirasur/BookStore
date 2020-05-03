package com.bridgelabz.bookstoreapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="Quantity_of_books")
@Data
@NoArgsConstructor
@ToString
public class QuantityOfBooks {

	@Id
	@Column(name = "quantity_of_books", nullable = false)
	private long quantityOfBook;

	
}
