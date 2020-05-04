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


@RestController
@RequestMapping("/cart")
@PropertySource("classpath:message.properties")
@CrossOrigin("*")
//@Api(value="bookStore", description="Operations pertaining to user in Online Store")
public class CartController {

	@Autowired
	private CartService cartService;

	@Autowired
	private Environment env;
	
	//@ApiOperation(value = "Adding the books to the Cartlist",response = Iterable.class)
	@PostMapping(value="/add_books_cart")
	public ResponseEntity<UserResponse> addBooksToCart(@PathVariable("token") String token,@RequestParam("noteId") long noteId) throws Exception {
		    User cart = cartService.addBooksToCart(token,noteId);
		    return ResponseEntity.status(200)
					.body(new UserResponse(env.getProperty("200"), "200-ok", cart));
					
	}

	//@ApiOperation(value = "Adding the books to the Cartlist",response = Iterable.class)
	@GetMapping(value="/get_cart")
	public ResponseEntity<UserResponse> getBooksfromCart(@PathVariable("token") String token) throws Exception {
		    List<CartDetails> cartdetails = cartService.getBooksfromCart(token);
		    return ResponseEntity.status(200)
					.body(new UserResponse(env.getProperty("200"), "200-ok", cartdetails));
	}
	
	//@ApiOperation(value = "Adding the books to the Cartlist",response = Iterable.class)
	@PostMapping(value="/add_booksquantity_cart/{token}")
	public ResponseEntity<UserResponse> addBooksQuantityToCart(@PathVariable("token") String token,@RequestParam("noteId") long noteId,@RequestParam("quantity") long quantity) throws Exception {
		   User cartdetails = cartService.addBooksQuantityToCart(token, noteId, quantity);
		    return ResponseEntity.status(200)
					.body(new UserResponse(env.getProperty("501"), "200-ok", cartdetails));  	
	}
	
	//@ApiOperation(value = "Adding the books to the Cartlist",response = Iterable.class)
	@PostMapping(value="/remove_books_cart/{token}")
	public ResponseEntity<UserResponse> removeBooksToCart(@PathVariable("token") String token,@RequestParam("noteId") long noteId) throws Exception {
		User cartdetails = cartService.removeBooksToCart(token,noteId);
		return ResponseEntity.status(200)
				.body(new UserResponse(env.getProperty("501"), "200-ok", cartdetails));    
		
					
	}
}