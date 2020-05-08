package com.bridgelabz.bookstoreapi.service;

import java.util.List;

import com.bridgelabz.bookstoreapi.entity.CartDetails;
import com.bridgelabz.bookstoreapi.entity.User;

public interface CartService {

	
	List<CartDetails> addBooksToCart(String token, long bookId);

	List<CartDetails> getBooksfromCart(String token);

	List<CartDetails> addBooksQuantityToCart(String token, long noteId, long quantity);

	List<CartDetails> removeBooksToCart(String token, long bookId);

	boolean verifyBookInCart(String token, long bookId);

}
