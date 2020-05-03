package com.bridgelabz.bookstoreapi.service;

import java.util.List;

import com.bridgelabz.bookstoreapi.entity.CartDetails;
import com.bridgelabz.bookstoreapi.entity.User;

public interface CartService {

	
	User addBooksToCart(String token, long bookId);

	List<CartDetails> getBooksfromCart(String token);

	User addBooksQuantityToCart(String token, long cartId, long quantity);

}
