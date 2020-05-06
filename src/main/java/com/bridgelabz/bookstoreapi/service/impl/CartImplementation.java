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
	public void addBooksToCart(String token, Long bookId, Long quantity) {
		Long id = jwt.decodeToken(token);
		
		User user = userRepository.findUserById(id)
				.orElseThrow(() -> new UserException(401, env.getProperty("104")));
		
		boolean notExist = user.getCartBooks().stream().noneMatch(cart -> cart.getBook().getBookId().equals(bookId));
		
		if(notExist) {
			Book book = bookRepository.findById(bookId)
					.orElseThrow(() -> new UserException(201, env.getProperty("4041")));
			CartDetails cart = new CartDetails(quantity, book);
			user.getCartBooks().add(cart);
			cartRepository.save(cart);
			userRepository.save(user);
		}
		else {
			throw new UserException(500, env.getProperty("505"));
		}
	       	
	}
	
	@Transactional
	@Override
	public void addBooksQuantityToCart(String token, Long bookId,Long quantity) {
		
		Long id = jwt.decodeToken(token);
		User user = userRepository.findUserById(id)
				.orElseThrow(() -> new UserException(401, env.getProperty("104")));
		CartDetails crt =  user.getCartBooks().stream().filter(cart -> cart.getBook().getBookId().equals(bookId)).findFirst().orElseThrow(() -> new UserException(404, env.getProperty("507")));
		crt.setQuantityOfBook(quantity);
		cartRepository.save(crt);
		userRepository.save(user);
	}

	@Transactional
	@Override
	public List<CartDetails> getBooksfromCart(String token) {
		Long id = jwt.decodeToken(token);
		
		User user = userRepository.findUserById(id)
				.orElseThrow(() -> new UserException(401, env.getProperty("104")));
	 List<CartDetails> cartBooks = user.getCartBooks();
	 cartBooks.forEach(cart -> {
		 System.out.println(cart.getBook().getBookName()+" ---> "+cart.getQuantityOfBook());
	 });
	 return cartBooks;
	}

	@Transactional
	@Override
	public void removeBooksToCart(String token, Long bookId) {
		
		Long id =  jwt.decodeToken(token);
		
		User user = userRepository.findUserById(id)
				.orElseThrow(() -> new UserException(201, env.getProperty("104")));
		CartDetails crt =  user.getCartBooks().stream().filter(cart -> cart.getBook().getBookId().equals(bookId)).findFirst().orElseThrow(() -> new UserException(404, env.getProperty("507")));
		
		user.getCartBooks().remove(crt);
		
		userRepository.save(user);
		
	}
}
