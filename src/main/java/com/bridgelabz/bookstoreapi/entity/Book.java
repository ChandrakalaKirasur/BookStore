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

import com.bridgelabz.bookstoreapi.dto.BookDTO;

@Entity
@Table(name="books")
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	
	@Column(name = "book_image")
	private String bookImage;
	
	@Column(name = "book_description", nullable = false)
	private String bookDescription;
	
	@Column(name = "book_verified")
	private boolean isBookVerified;
	
	@Column(name = "book_added_time", nullable = false)
	private LocalDateTime bookAddedTime;	

	@Column(name = "book_updated_time", nullable = false)
	private LocalDateTime bookUpdatedTime;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "book_id")
	private List<ReviewAndRating> reviewRating;
	
	public Book(BookDTO dto) {
		this.bookName = dto.getBookName();
		this.bookAuthor = dto.getBookAuthor();
		this.bookPrice = dto.getBookPrice();
		this.noOfBooks = dto.getNoOfBooks();
		this.bookDescription = dto.getBookDescription();
		this.isBookVerified = false;
		this.bookAddedTime = LocalDateTime.now();
		this.bookUpdatedTime = LocalDateTime.now();
	}

	
	public Book() {
		super();
	}


	public boolean isBookVerified() {
		return isBookVerified;
	}


	public void setBookVerified(boolean isBookVerified) {
		this.isBookVerified = isBookVerified;
	}


	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getBookAuthor() {
		return bookAuthor;
	}

	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}

	public List<ReviewAndRating> getReviewRating() {
		return reviewRating;
	}


	public void setReviewRating(List<ReviewAndRating> reviewRating) {
		this.reviewRating = reviewRating;
	}


	public Double getBookPrice() {
		return bookPrice;
	}

	public void setBookPrice(Double bookPrice) {
		this.bookPrice = bookPrice;
	}

	public Long getNoOfBooks() {
		return noOfBooks;
	}

	public void setNoOfBooks(Long noOfBooks) {
		this.noOfBooks = noOfBooks;
	}

	public String getBookImage() {
		return bookImage;
	}

	public void setBookImage(String bookImage) {
		this.bookImage = bookImage;
	}

	public String getBookDescription() {
		return bookDescription;
	}

	public void setBookDescription(String bookDescription) {
		this.bookDescription = bookDescription;
	}

	public LocalDateTime getBookAddedTime() {
		return bookAddedTime;
	}

	public void setBookAddedTime(LocalDateTime bookAddedTime) {
		this.bookAddedTime = bookAddedTime;
	}

	public LocalDateTime getBookUpdatedTime() {
		return bookUpdatedTime;
	}

	public void setBookUpdatedTime(LocalDateTime bookUpdatedTime) {
		this.bookUpdatedTime = bookUpdatedTime;
	}
	
	
}
