package com.bridgelabz.bookstoreapi.service;

import java.util.List;

import com.bridgelabz.bookstoreapi.dto.BookDTO;
import com.bridgelabz.bookstoreapi.dto.RatingReviewDTO;
import com.bridgelabz.bookstoreapi.entity.Book;
import com.bridgelabz.bookstoreapi.entity.ReviewAndRating;

public interface BookService {

	public Book addBook(BookDTO bookDTO, String token);
	public void updateBook(BookDTO bookDTO, String token, Long bookId);
	public void deleteBook(String token, Long bookId);
	public List<Book> getBooks(Integer pageNo);
	public List<Book> getBooksSortedByPriceLow(Integer pageNo);
	public List<Book> getBooksSortedByPriceHigh(Integer pageNo);
	public List<Book> getBooksSortedByArrival(Integer pageNo);
	public List<Book> getBookByNameAndAuthor(String text) ;
	public void writeReviewAndRating(String token, RatingReviewDTO rrDTO, Long bookId);
	public List<ReviewAndRating> getRatingsOfBook(Long bookId);
	public Integer getBooksCount();
<<<<<<< HEAD
	public List<Book> getSellerBooks(String token);
=======
	List<Book> getAllBooks();
	List<Book> VerifyBook(Long bookId);
>>>>>>> 6b47916c1d8dcf093b4c55584449f2b8355834d6
	
}
