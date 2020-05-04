package com.bridgelabz.bookstoreapi.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bridgelabz.bookstoreapi.entity.Book;


@Service
public interface IServiceElasticSearch {
	
	public String addBook(Book book) throws IOException;

	public Book findById(String id) throws Exception;

	public String upDateBook(Book note) throws Exception;

	public String deleteBook(String id) throws IOException;

	 List<Book> getNoteByTitleAndDescription(String text);
}