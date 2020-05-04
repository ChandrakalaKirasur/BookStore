package com.bridgelabz.bookstoreapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
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
import com.bridgelabz.bookstoreapi.entity.WhishListDetails;
import com.bridgelabz.bookstoreapi.response.UserResponse;
import com.bridgelabz.bookstoreapi.service.WhishListService;

@RestController
@RequestMapping("/whishList")
@PropertySource("classpath:message.properties")
@CrossOrigin("*")
//@Api(value="bookStore", description="Operations pertaining to Cart in Online Store")
public class WhishListController {

	@Autowired
	private WhishListService whishlistService;

	@Autowired
	private Environment env;
	
	//@ApiOperation(value = "Adding the books to the Whishlist",response = Iterable.class)
	@PostMapping(value="/add_books_WhishList/{token}")
	public ResponseEntity<UserResponse> addBooksToWhilist(@PathVariable("token") String token,@RequestParam("bookId") long bookId) throws Exception {
		    User whishlist = whishlistService.addBooksToWhishList(token,bookId);
		    return ResponseEntity.status(HttpStatus.CREATED)
					.body(new UserResponse(env.getProperty("601"), "200-ok", whishlist));  	
	}
	
	//@ApiOperation(value = "Getting the books from Whishlist",response = Iterable.class)
	@GetMapping(value="/books_cart/{token}")
	public ResponseEntity<UserResponse> getBooksfromCart(@PathVariable("token") String token) throws Exception {
		    List<WhishListDetails> whishlist = whishlistService.getBooksfromWhishList(token);
		    return ResponseEntity.status(HttpStatus.CREATED)
					.body(new UserResponse(env.getProperty("611"), "200-ok", whishlist));  
	}
	
	//@ApiOperation(value = "Removing the books to the Whishlist",response = Iterable.class)
	@PostMapping(value="/remove_books_WhishList/{token}")
	public ResponseEntity<UserResponse> removeBooksToWhilist(@PathVariable("token") String token,@RequestParam("bookId") long bookId) throws Exception {
		    User whishlist = whishlistService.removeBooksToWhishList(token,bookId);
		    return ResponseEntity.status(HttpStatus.CREATED)
					.body(new UserResponse(env.getProperty("605"), "200-ok", whishlist));  	
	}
}
