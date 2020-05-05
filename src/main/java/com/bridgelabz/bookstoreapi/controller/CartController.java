package com.bridgelabz.bookstoreapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bookstoreapi.entity.CartDetails;
import com.bridgelabz.bookstoreapi.entity.User;
import com.bridgelabz.bookstoreapi.response.UserResponse;
import com.bridgelabz.bookstoreapi.service.CartService;
import com.bridgelabz.bookstoreapi.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/cart")
@PropertySource("classpath:message.properties")
@CrossOrigin("*")
@Api(value="bookStore", description="Operations pertaining to Cart in Online Store")
public class CartController {

	@Autowired
	private CartService cartService;

	@Autowired
	private Environment env;
	
	@ApiOperation(value = "Adding the books to the Cartlist",response = Iterable.class)
	@PostMapping(value="/add_books_cart/{token}")
	public ResponseEntity<UserResponse> addBooksToCart(@PathVariable("token") String token,@RequestParam("bookId") long bookId) throws Exception {
		    User cart = cartService.addBooksToCart(token,bookId);
		    return ResponseEntity.status(200)
					.body(new UserResponse(env.getProperty("500"), "200-ok", cart));
					
	}

	@ApiOperation(value = "Getting the books from the Cartlist",response = Iterable.class)
	@GetMapping(value="/get_cart/{token}")
	public ResponseEntity<UserResponse> getBooksfromCart(@PathVariable("token") String token) throws Exception {
		    List<CartDetails> cartdetails = cartService.getBooksfromCart(token);
		    return ResponseEntity.status(200)
					.body(new UserResponse(env.getProperty("503"), "200-ok", cartdetails));
	}
	
	@ApiOperation(value = "Adding the quantityofbooks to the Cartlist",response = Iterable.class)
	@PostMapping(value="/add_booksquantity_cart/{token}")
	public ResponseEntity<UserResponse> addBooksQuantityToCart(@PathVariable("token") String token,@RequestParam("bookId") long bookId,@RequestParam("quantity") long quantity) throws Exception {
		   User cartdetails = cartService.addBooksQuantityToCart(token, bookId, quantity);
		    return ResponseEntity.status(200)
					.body(new UserResponse(env.getProperty("502"), "200-ok", cartdetails));  	
	}
	
	@ApiOperation(value = "Removing the books to the Cartlist",response = Iterable.class)
	@PostMapping(value="/remove_books_cart/{token}")
	public ResponseEntity<UserResponse> removeBooksToCart(@PathVariable("token") String token,@RequestParam("bookId") long bookId) throws Exception {
		User cartdetails = cartService.removeBooksToCart(token,bookId);
		return ResponseEntity.status(200)
				.body(new UserResponse(env.getProperty("504"), "200-ok", cartdetails));    
		
					
	}
}
