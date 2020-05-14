package com.bridgelabz.bookstoreapi.service;

import java.util.List;

import com.bridgelabz.bookstoreapi.entity.Book;
import com.bridgelabz.bookstoreapi.entity.User;

public interface WhishListService {

    List<Book> addBooksToWhishList(String token, long bookId);
	
    List<Book> getBooksfromWhishList(String token);

    List<Book> removeBooksToWhishList(String token, long bookId);

	int getcountBooksfromWhishList(String token);
    
	boolean verifyBookInWishlist(String token, Long bookId);
}
