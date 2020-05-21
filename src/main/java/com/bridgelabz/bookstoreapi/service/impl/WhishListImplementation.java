package com.bridgelabz.bookstoreapi.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstoreapi.entity.Book;
import com.bridgelabz.bookstoreapi.entity.CartDetails;
import com.bridgelabz.bookstoreapi.entity.User;
import com.bridgelabz.bookstoreapi.exception.UserException;
import com.bridgelabz.bookstoreapi.repository.BookRepository;
import com.bridgelabz.bookstoreapi.repository.UserRepository;
import com.bridgelabz.bookstoreapi.service.WhishListService;
import com.bridgelabz.bookstoreapi.utility.JWTUtil;

@Service
@PropertySource("classpath:message.properties")
public class WhishListImplementation implements WhishListService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private JWTUtil jwt;
	
	
	@Autowired
	private Environment env;
	
	@Override
	public List<Book> addBooksToWhishList(String token, long bookId) {
		Long id = jwt.decodeToken(token);
		
		
		User user = userRepository.findById(id)
				.orElseThrow(() -> new UserException(201, env.getProperty("104")));
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new UserException(201, env.getProperty("4041")));
		/**
		 * Getting the bookList
		 */
		if( user.getWhilistBooks() ==null) {
			user.getWhilistBooks().add(book);
		}
		
		/**
		 * Checking whether book is already present r not
		 */
		Optional<Book> cartbook = user.getWhilistBooks().stream().filter(t -> t.getBookId()==bookId).findFirst();
		
		if(cartbook.isPresent()) {
			throw new UserException(201, env.getProperty("605"));
		}else {
			user.getWhilistBooks().add(book);
		  
		}
		
		return userRepository.save(user).getWhilistBooks();
	}
	
	@Override
	public List<Book> removeBooksToWhishList(String token, long bookId) {
		
        long id = (Long) jwt.decodeToken(token);
		
		
		User user = userRepository.findUserById(id)
				.orElseThrow(() -> new UserException(201, env.getProperty("104")));
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new UserException(201, env.getProperty("104")));
		
		user.getWhilistBooks().remove(book);
	
		return userRepository.save(user).getWhilistBooks();
		
	}
	
	@Override
	public List<Book> getBooksfromWhishList(String token) {
		long id = (Long) jwt.decodeToken(token);
		
		User user = userRepository.findUserById(id)
				.orElseThrow(() -> new UserException(201, env.getProperty("104")));
	    List<Book> whishList = user.getWhilistBooks();
	  	return whishList;
	}
	
	@Override
	public int getcountBooksfromWhishList(String token) {
		long id = (Long) jwt.decodeToken(token);
		
		int noOfBooks = 0;
		User user = userRepository.findUserById(id)
				.orElseThrow(() -> new UserException(201, env.getProperty("104")));
	    List<Book> whishList = user.getWhilistBooks();
	    for(Book books:whishList) {
	    	if(books.getBookId()!=null) {
	    		noOfBooks++;
	    	}
	    }
	  	return noOfBooks;
	}

	@Transactional
	@Override
	public boolean verifyBookInWishlist(String token, Long bookId) {
		if(token != null) {
		List<Book> bookdetails = this.getBooksfromWhishList(token);
		for(Book whish:bookdetails) {
			if (whish.getBookId().equals(bookId)) {
				return true;
			}
		}
		}
		return false;
	}
	
}
