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
@Table(name="whish_list_details")
@Data
@NoArgsConstructor
@ToString
public class WhishListDetails {

	
	@Id
	@Column
	private LocalDateTime PlacedTime;

	@ManyToMany(cascade = CascadeType.ALL)
	private List<Book> BooksList;

	public LocalDateTime getPlacedTime() {
		return PlacedTime;
	}

	public void setPlacedTime(LocalDateTime placedTime) {
		PlacedTime = placedTime;
	}


	public List<Book> getBooksList() {
		return BooksList;
	}

	public void setBooksList(List<Book> booksList) {
		BooksList = booksList;
	}
	
	
}
