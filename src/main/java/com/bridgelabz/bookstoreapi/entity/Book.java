package com.bridgelabz.bookstoreapi.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.bridgelabz.bookstoreapi.dto.BookDTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="books")
@Data
@NoArgsConstructor
@ToString
public class Book {
	
	@Id
	@GenericGenerator(name = "idGen", strategy = "increment")
	@GeneratedValue(generator = "idGen")
	@Column(name = "book_id")
	private Long bookId;
	
	@Column(name = "book_name", nullable = false)
	private String bookName;
	
	@Column(name = "book_author", nullable = false)
	private String bookAuthor;
	
	@Column(name = "book_price", nullable = false)
	private Double bookPrice;
	
	@Column(name = "no_of_books", nullable = false)
	private Long noOfBooks;
	
	@Column(name = "book_image", nullable = false)
	private String bookImage;
	
	@Column(name = "book_description", nullable = false)
	private String bookDescription;
	
	@Column(name = "book_added_time", nullable = false)
	private LocalDateTime bookAddedTime;	

	@Column(name = "book_updated_time", nullable = false)
	private LocalDateTime bookUpdatedTime;
	
	public Book(BookDTO dto) {
		this.bookName = dto.getBookName();
		this.bookAuthor = dto.getBookAuthor();
		this.bookPrice = dto.getBookPrice();
		this.noOfBooks = dto.getNoOfBooks();
		this.bookDescription = dto.getBookDescription();
		this.bookAddedTime = LocalDateTime.now();
		this.bookUpdatedTime = LocalDateTime.now();
	}
}
