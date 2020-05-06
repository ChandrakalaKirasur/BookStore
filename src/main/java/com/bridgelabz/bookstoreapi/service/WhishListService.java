package com.bridgelabz.bookstoreapi.service;

import java.util.List;

import com.bridgelabz.bookstoreapi.entity.Book;
import com.bridgelabz.bookstoreapi.entity.User;

public interface WhishListService {

    User addBooksToWhishList(String token, long bookId);
	
    List<Book> getBooksfromWhishList(String token);

    User removeBooksToWhishList(String token, long bookId);
    
	
}
