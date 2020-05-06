package com.bridgelabz.bookstoreapi.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstoreapi.entity.Book;
import com.bridgelabz.bookstoreapi.entity.CartDetails;
import com.bridgelabz.bookstoreapi.entity.QuantityOfBooks;
import com.bridgelabz.bookstoreapi.entity.User;
import com.bridgelabz.bookstoreapi.exception.UserException;
import com.bridgelabz.bookstoreapi.repository.BookRepository;
import com.bridgelabz.bookstoreapi.repository.CartRepository;
import com.bridgelabz.bookstoreapi.repository.UserRepository;
import com.bridgelabz.bookstoreapi.service.CartService;
import com.bridgelabz.bookstoreapi.utility.JWTUtil;

@Service
@PropertySource("classpath:message.properties")
public class CartImplementation implements CartService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JWTUtil jwt;
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	
	@Autowired
	private Environment env;

	@Transactional
	@Override
	public User addBooksToCart(String token, long bookId) {
		long id = (Long) jwt.decodeToken(token);
		
		User user = userRepository.findUserById(id)
				.orElseThrow(() -> new UserException(401, env.getProperty("104")));
		
		CartDetails cart=new CartDetails();
		ArrayList<Book> booklist=new ArrayList<>();
		
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new UserException(201, env.getProperty("4041")));
		
		/**
		 * Getting the bookList
		 */
		List<Book> books = null;
		for(CartDetails d:user.getCartBooks()) {
			books=d.getBooksList();
		}
		/**
		 * For the first time adding the book the cartList
		 */
		if(books==null) {
			booklist.add(book);
			cart.setPlaceTime(LocalDateTime.now());
			cart.setBooksList(booklist);
		    user.getCartBooks().add(cart);
		    return userRepository.save(user);
		}
		/**
		 * Checking whether book is already present r not
		 */
		Optional<Book> cartbook = books.stream().filter(t -> t.getBookId() == bookId).findFirst();
		
		if(cartbook.isPresent()) {
			return null;
		}else {
	    
		booklist.add(book);
		cart.setPlaceTime(LocalDateTime.now());
		cart.setBooksList(booklist);
		
	    user.getCartBooks().add(cart);
		}
	    
		return userRepository.save(user);
	       	
	}
	
	@Transactional
	@Override
	public User addBooksQuantityToCart(String token, long bookId,long quantity) {
		
		long id = (Long) jwt.decodeToken(token);
		
		QuantityOfBooks cartquantity=new QuantityOfBooks();
		
		User user = userRepository.findUserById(id)
				.orElseThrow(() -> new UserException(401, env.getProperty("104")));
		
		ArrayList<String> list=new ArrayList<String>();
	        user.getCartBooks().forEach((cart)->{
	        	/**
	        	 * checking the number of books available
	        	 */
	        	boolean notExist = cart.getBooksList().stream().noneMatch(books-> books.getBookId()==bookId && quantity<books.getNoOfBooks());
	        	
	        	if(notExist) {
	        		list.add(token);
	        	}else {
	        		cartquantity.setQuantityOfBook(quantity);
	       			cart.setQuantityOfBooks(cartquantity);
	        	}
	     
	       	});
	       if(list.contains(token)) {
	    	   return null;
	       }
	        return userRepository.save(user);
	        
	}

	@Transactional
	@Override
	public List<CartDetails> getBooksfromCart(String token) {
		long id = (Long) jwt.decodeToken(token);
		
		User user = userRepository.findUserById(id)
				.orElseThrow(() -> new UserException(401, env.getProperty("104")));
	 List<CartDetails> cartBooks = user.getCartBooks();
	 return cartBooks;
	}

	@Transactional
	@Override
	public User removeBooksToCart(String token, long bookId) {
		
		long id = (Long) jwt.decodeToken(token);
		
		User user = userRepository.findUserById(id)
				.orElseThrow(() -> new UserException(201, env.getProperty("104")));
		
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new UserException(201, env.getProperty("104")));
		
		user.getCartBooks().forEach((books)->{
			 books.getBooksList().remove(book);
		});
		
		return userRepository.save(user);
	}
}
