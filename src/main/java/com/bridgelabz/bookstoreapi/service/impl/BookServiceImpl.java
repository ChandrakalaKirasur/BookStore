package com.bridgelabz.bookstoreapi.service.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstoreapi.constants.Constants;
import com.bridgelabz.bookstoreapi.dto.BookDTO;
import com.bridgelabz.bookstoreapi.entity.Book;
import com.bridgelabz.bookstoreapi.entity.Seller;
import com.bridgelabz.bookstoreapi.exception.BookException;
import com.bridgelabz.bookstoreapi.exception.SellerException;
import com.bridgelabz.bookstoreapi.repository.BookRepository;
import com.bridgelabz.bookstoreapi.repository.SellerRepository;
import com.bridgelabz.bookstoreapi.service.BookService;
import com.bridgelabz.bookstoreapi.utility.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@PropertySource("classpath:message.properties")
public class BookServiceImpl implements BookService{

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private SellerRepository sellerRepository;
	
	@Autowired
	private JWTUtil jwt;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private RestHighLevelClient client;

	@Autowired
	private ObjectMapper objectMapper;
	
	public void addBook(BookDTO bookDTO, String token) {
		Long sId = jwt.decodeToken(token);
		Book book = new Book(bookDTO);
		Seller seller = sellerRepository.findById(sId).orElseThrow(() -> new SellerException(404, env.getProperty("104")));
		List<Book> books =  seller.getSellerBooks();
		boolean notExist = books.stream().noneMatch(bk -> bk.getBookName().equals(bookDTO.getBookName()));
		if(notExist) {
		seller.getSellerBooks().add(book);
		Book bookSave = bookRepository.save(book);
		sellerRepository.save(seller);
		Map<String, Object> documentMapper = objectMapper.convertValue(bookSave, Map.class);
		IndexRequest indexRequest = new IndexRequest(Constants.INDEX, Constants.TYPE, String.valueOf(bookSave.getBookId()))
				.source(documentMapper);
		try {
			client.index(indexRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
		else {
			throw new BookException(500, env.getProperty("5001"));
		}
	}
	
	@Transactional
	public void updateBook(BookDTO bookDTO, String token, Long bookId) {
		Long sId = jwt.decodeToken(token);
		Seller seller = sellerRepository.findById(sId).orElseThrow(() -> new SellerException(404, env.getProperty("104")));
		List<Book> books = seller.getSellerBooks();
		Book filteredBook = books.stream().filter(book -> book.getBookId().equals(bookId)).findFirst()
				.orElseThrow(() -> new BookException(404, env.getProperty("4041")));
		filteredBook.setBookName(bookDTO.getBookName());
		filteredBook.setBookAuthor(bookDTO.getBookAuthor());
		filteredBook.setBookDescription(bookDTO.getBookDescription());
		filteredBook.setBookPrice(bookDTO.getBookPrice());
		filteredBook.setNoOfBooks(bookDTO.getNoOfBooks());
		filteredBook.setBookUpdatedTime(LocalDateTime.now());
		Book bookUpdate = bookRepository.save(filteredBook);
		sellerRepository.save(seller);
		updateBookInES(bookUpdate);
	}
	
	@Transactional
	public void deleteBook(String token, Long bookId) {
		Long sId = jwt.decodeToken(token);
		Seller seller = sellerRepository.findById(sId).orElseThrow(() -> new SellerException(404, env.getProperty("104")));
		List<Book> books = seller.getSellerBooks();
		Book filteredBook = books.stream().filter(book -> book.getBookId().equals(bookId)).findFirst()
				.orElseThrow(() -> new BookException(404, env.getProperty("4041")));
		books.remove(filteredBook);
		bookRepository.delete(filteredBook);
		sellerRepository.save(seller);
		DeleteRequest deleteRequest = new DeleteRequest(Constants.INDEX, Constants.TYPE,String.valueOf(filteredBook.getBookId()));
		try {
			client.delete(deleteRequest,RequestOptions.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<Book> getBooks(Integer pageNo){
		Integer start = (pageNo-1)*10;
//		bookRepository.findBook(start).forEach(book -> {
//			Map<String, Object> documentMapper = objectMapper.convertValue(book, Map.class);
//			IndexRequest indexRequest = new IndexRequest(Constants.INDEX, Constants.TYPE, String.valueOf(book.getBookId()))
//					.source(documentMapper);
//			try {
//				client.index(indexRequest, RequestOptions.DEFAULT);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		});
		return bookRepository.findBook(start);
	}
	
	public List<Book> getBooksSortedByPriceLow(Integer pageNo){
		Integer start = (pageNo-1)*10;
		return bookRepository.findBookSortedByPriceLow(start);
	}
	
	public List<Book> getBooksSortedByPriceHigh(Integer pageNo){
		Integer start = (pageNo-1)*10;
		return bookRepository.findBookSortedByPriceHigh(start);
	}
	
	public List<Book> getBooksSortedByArrival(Integer pageNo){
		Integer start = (pageNo-1)*10;
		return bookRepository.findBookSortedByArrival(start);
	}
	
	
	public List<Book> getBookByNameAndAuthor(String text) {
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.indices(Constants.INDEX);
		searchRequest.types(Constants.TYPE);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		QueryBuilder query = QueryBuilders.boolQuery()
				.should(QueryBuilders.queryStringQuery(text).lenient(true).field("bookName").field("bookAuthor"))
				.should(QueryBuilders.queryStringQuery("*" + text + "*").lenient(true).field("bookName").field("bookAuthor"));
//				.mustNot(QueryBuilders.existsQuery(""));
		searchSourceBuilder.query(query);
		searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse = null;
		try {
			searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return getSearchResult(searchResponse);
	}

	private List<Book> getSearchResult(SearchResponse response) {

		SearchHit[] searchHit = response.getHits().getHits();

		List<Book> books = new ArrayList<>();

		for (SearchHit hit : searchHit) {
			books.add(objectMapper.convertValue(hit.getSourceAsMap(), Book.class));
		}
		return books;
	}
	
	private void updateBookInES(Book filteredBook) {
		UpdateRequest updateRequest = new UpdateRequest(Constants.INDEX, Constants.TYPE,
				String.valueOf(filteredBook.getBookId()));
		updateRequest.doc(objectMapper.convertValue(filteredBook, Map.class));
		try {
			client.update(updateRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
