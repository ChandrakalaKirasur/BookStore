package com.bridgelabz.bookstoreapi.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstoreapi.entity.Book;
import com.bridgelabz.bookstoreapi.entity.CartDetails;
import com.bridgelabz.bookstoreapi.entity.QuantityOfBooks;
import com.bridgelabz.bookstoreapi.entity.User;
import com.bridgelabz.bookstoreapi.entity.WhishListDetails;
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
		
		WhishListDetails whishlist=new WhishListDetails();
		ArrayList<Book> booklist=new ArrayList<>();
		
		User user = userRepository.findUserById(id)
				.orElseThrow(() -> new UserException(201, env.getProperty("104")));
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new UserException(201, env.getProperty("4041")));
		/**
		 * Getting the bookList
		 */
		List<Book> books=null;
		for(WhishListDetails d:user.getWhilistBooks()) {
			books=d.getBooksList();
		}
		/**
		 * Checking whether book is already present r not
		 */
		Optional<Book> cartbook = books.stream().filter(t -> t.getBookId() == bookId).findFirst();
		
		if(cartbook.isPresent()) {
			throw new UserException(201, env.getProperty("605"));
		}else {
			booklist.add(book);
			whishlist.setPlacedTime(LocalDateTime.now());
			whishlist.setBooksList(booklist);
		    user.getWhilistBooks().add(whishlist);
		}
		
		return userRepository.save(user);
	}
	
	@Override
	public User removeBooksToWhishList(String token, long bookId) {
		
        long id = (Long) jwt.decodeToken(token);
		
		WhishListDetails whishlist=new WhishListDetails();
		
		User user = userRepository.findUserById(id)
				.orElseThrow(() -> new UserException(201, env.getProperty("104")));
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new UserException(201, env.getProperty("104")));
		
		user.getWhilistBooks().forEach((notes)->{
			notes.getBooksList().remove(book);
		});

	    user.getWhilistBooks().add(whishlist);
	   
	
		return userRepository.save(user);
		
	}
	
	@Override
	public List<WhishListDetails> getBooksfromWhishList(String token) {
		long id = (Long) jwt.decodeToken(token);
		
		User user = userRepository.findUserById(id)
				.orElseThrow(() -> new UserException(201, env.getProperty("104")));
	    List<WhishListDetails> whishList = user.getWhilistBooks();
	  	return whishList;
	}
	

	
}
