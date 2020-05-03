package com.bridgelabz.bookstoreapi.service;

import com.bridgelabz.bookstoreapi.dto.BookDTO;

public interface BookService {

	public void addBook(BookDTO bookDTO, String token);
	public void updateBook(BookDTO bookDTO, String token, Long bookId);
	public void deleteBook(String token, Long bookId);
	
}
