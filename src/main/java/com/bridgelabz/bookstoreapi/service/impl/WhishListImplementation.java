package com.bridgelabz.bookstoreapi.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstoreapi.entity.Book;

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
	public User addBooksToWhishList(String token, long bookId) {
		long id = (Long) jwt.decodeToken(token);
		
		
		User user = userRepository.findUserById(id)
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
		Optional<Book> cartbook = user.getWhilistBooks().stream().filter(t -> t.getBookId() == bookId).findFirst();
		
		if(cartbook.isPresent()) {
			throw new UserException(201, env.getProperty("605"));
		}else {
			user.getWhilistBooks().add(book);
		  
		}
		
		return userRepository.save(user);
	}
	
	@Override
	public User removeBooksToWhishList(String token, long bookId) {
		
        long id = (Long) jwt.decodeToken(token);
		
		
		User user = userRepository.findUserById(id)
				.orElseThrow(() -> new UserException(201, env.getProperty("104")));
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new UserException(201, env.getProperty("104")));
		
		user.getWhilistBooks().remove(book);
	
		return userRepository.save(user);
		
	}
	
	@Override
	public List<Book> getBooksfromWhishList(String token) {
		long id = (Long) jwt.decodeToken(token);
		
		User user = userRepository.findUserById(id)
				.orElseThrow(() -> new UserException(201, env.getProperty("104")));
	    List<Book> whishList = user.getWhilistBooks();
	  	return whishList;
	}
	

	
}
