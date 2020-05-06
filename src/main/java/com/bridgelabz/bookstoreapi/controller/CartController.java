package com.bridgelabz.bookstoreapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
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
@Api(value="bookStore", description="Operations pertaining to Cart in Book Store")
public class CartController {

	@Autowired
	private CartService cartService;

	@Autowired
	private Environment env;
	
	@ApiOperation(value = "Adding the books to the Cartlist",response = Iterable.class)
	@PostMapping(value="/add_books_cart/{token}")
	public ResponseEntity<UserResponse> addBooksToCart(@RequestHeader("token") String token,@RequestParam("bookId") Long bookId,@RequestParam("quntity") Long quantity) {
		    cartService.addBooksToCart(token,bookId,quantity);
//		    if(cart!=null)
		    return ResponseEntity.status(200)
					.body(new UserResponse(env.getProperty("500"),null,HttpStatus.OK));
//		    return ResponseEntity.status(200)
//					.body(new UserResponse(env.getProperty("505"),  cart,HttpStatus.NOT_FOUND));			
	}

	@ApiOperation(value = "Getting the books from the Cartlist",response = Iterable.class)
	@GetMapping(value="/get_cart/{token}")
	public ResponseEntity<UserResponse> getBooksfromCart(@RequestHeader("token") String token) throws Exception {
		    List<CartDetails> cartdetails = cartService.getBooksfromCart(token);
		    return ResponseEntity.status(200)
					.body(new UserResponse(env.getProperty("503"), cartdetails,HttpStatus.OK));
	}
	
	@ApiOperation(value = "Adding the quantityofbooks to the Cartlist",response = Iterable.class)
	@PostMapping(value="/add_booksquantity_cart/{token}")
	public ResponseEntity<UserResponse> addBooksQuantityToCart(@RequestHeader("token") String token,@RequestParam("bookId") Long bookId,@RequestParam("quantity") long quantity) {
		  cartService.addBooksQuantityToCart(token, bookId, quantity);
		    return ResponseEntity.status(200)
					.body(new UserResponse(env.getProperty("502"), null,HttpStatus.OK));  	
	}
	
	@ApiOperation(value = "Removing the books to the Cartlist",response = Iterable.class)
	@DeleteMapping(value="/remove_books_cart/{token}")
	public ResponseEntity<UserResponse> removeBooksToCart(@RequestHeader("token") String token,@RequestParam("bookId") Long bookId) {
		cartService.removeBooksToCart(token,bookId);
		return ResponseEntity.status(200)
				.body(new UserResponse(env.getProperty("504"),  null,HttpStatus.OK));    
		
					
	}
}
