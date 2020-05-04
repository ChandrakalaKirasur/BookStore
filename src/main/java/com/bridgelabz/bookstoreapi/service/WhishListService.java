package com.bridgelabz.bookstoreapi.service;

import java.util.List;

import com.bridgelabz.bookstoreapi.entity.User;
import com.bridgelabz.bookstoreapi.entity.WhishListDetails;

public interface WhishListService {

    User addBooksToWhishList(String token, long bookId);
	
    List<WhishListDetails> getBooksfromWhishList(String token);

    User removeBooksToWhishList(String token, long bookId);
    
	User addBooksQuantityToWhilist(String token, long bookId, long quantity);

	
}
