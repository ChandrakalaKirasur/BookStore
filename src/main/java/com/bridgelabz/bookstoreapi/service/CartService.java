package com.bridgelabz.bookstoreapi.service;

import java.util.List;

import com.bridgelabz.bookstoreapi.dto.CartdetailsDto;
import com.bridgelabz.bookstoreapi.entity.CartDetails;
import com.bridgelabz.bookstoreapi.entity.User;

public interface CartService {

	
	List<CartDetails> addBooksToCart(String token, long bookId);

	List<CartDetails> getBooksfromCart(String token);

	boolean removeBooksToCart(String token, Long bookId);
	
	boolean verifyBookInCart(String token, Long bookId);

	CartDetails addBooksQuantityInCart(String token, Long bookId, CartdetailsDto bookQuantityDetails);
	
	int getCountOfBooks(String token);

	CartDetails descBooksQuantityInCart(String token, Long bookId, CartdetailsDto bookQuantityDetails);

}
