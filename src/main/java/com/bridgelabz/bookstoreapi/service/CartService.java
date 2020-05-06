package com.bridgelabz.bookstoreapi.service;

import java.util.List;

import com.bridgelabz.bookstoreapi.entity.CartDetails;
import com.bridgelabz.bookstoreapi.entity.User;

public interface CartService {

	
	public void addBooksToCart(String token, Long bookId, Long quantity);

	public void addBooksQuantityToCart(String token, Long bookId,Long quantity);

	public List<CartDetails> getBooksfromCart(String token);
	
	public void removeBooksToCart(String token, Long bookId);

}
