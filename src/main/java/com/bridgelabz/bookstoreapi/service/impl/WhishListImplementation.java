package com.bridgelabz.bookstoreapi.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstoreapi.entity.Book;
import com.bridgelabz.bookstoreapi.entity.QuantityOfBooks;
import com.bridgelabz.bookstoreapi.entity.User;
import com.bridgelabz.bookstoreapi.entity.WhishListDetails;
import com.bridgelabz.bookstoreapi.exception.UserException;
import com.bridgelabz.bookstoreapi.repository.UserRepository;
import com.bridgelabz.bookstoreapi.service.WhishListService;
import com.bridgelabz.bookstoreapi.utility.JWTUtil;

@Service
@PropertySource("classpath:message.properties")
public class WhishListImplementation implements WhishListService{

	@Autowired
	private UserRepository userRepository;
	
//	@Autowired
//	private BookRepository bookRepository;
	
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
//		Books book = bookRepository.findBookById(bookId)
//				.orElseThrow(() -> new UserException(201, env.getProperty("104")));
//		
//		booklist.add(book);
//		whishlist.setBooksList(booklist);
//	    user.getWhilistBooks().add(whishlist);
	   
	
		return userRepository.save(user);
	}
	
	@Override
	public User removeBooksToWhishList(String token, long bookId) {
		
        long id = (Long) jwt.decodeToken(token);
		
		WhishListDetails whishlist=new WhishListDetails();
		
		User user = userRepository.findUserById(id)
				.orElseThrow(() -> new UserException(201, env.getProperty("104")));
//		Books book = bookRepository.findBookById(bookId)
//				.orElseThrow(() -> new UserException(201, env.getProperty("104")));
//		
//		user.getWhilistBooks().forEach((notes)->{
//			notes.getBooksList().remove(book);
//		});
//
//	    user.getWhilistBooks().add(whishlist);
	   
	
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
	
	@Override
	public User addBooksQuantityToWhilist(String token, long wishListId,long quantity) {
		
		long id = (Long) jwt.decodeToken(token);
		QuantityOfBooks cartquantity=new QuantityOfBooks();
		User user = userRepository.findUserById(id)
				.orElseThrow(() -> new UserException(201, env.getProperty("104")));
		
	        user.getWhilistBooks().forEach((data)->{
	        	
	       		if(data.getWhishListId()==wishListId) {
	       			cartquantity.setQuantityOfBook(quantity);
	       			data.getQuantityOfBooks().add(cartquantity);
	       		}
	       	});
	        return user;
	}
	
}
