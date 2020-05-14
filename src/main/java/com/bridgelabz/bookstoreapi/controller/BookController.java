package com.bridgelabz.bookstoreapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.bookstoreapi.dto.BookDTO;
import com.bridgelabz.bookstoreapi.dto.RatingReviewDTO;
import com.bridgelabz.bookstoreapi.response.Response;
import com.bridgelabz.bookstoreapi.service.AwsS3Service;
import com.bridgelabz.bookstoreapi.service.BookService;
import com.bridgelabz.bookstoreapi.utility.ImageType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/book")
@CrossOrigin("*")
@Api(value="Book_Details", description="Operations pertaining to store book details")
@PropertySource("classpath:message.properties")
public class BookController {

	@Autowired
	private BookService bookService;
	
	@Autowired
	private AwsS3Service awsService;
	
	@Autowired
	private Environment env;
	
	@ApiOperation(value = "Add a Book Details")
	@PostMapping("/addbook")
	public ResponseEntity<Response> addBook(@RequestBody BookDTO bookDTO,@RequestHeader(name="token") String token){
		bookService.addBook(bookDTO, token);
		return ResponseEntity.ok().body(new Response(HttpStatus.OK.value(),env.getProperty("2001")));
	}
	
	@ApiOperation(value = "Update a Book Details")
	@PutMapping("/update")
	public ResponseEntity<Response> updateBook(@RequestBody BookDTO bookDTO,@RequestHeader(name="token") String token, @RequestParam Long bookId){
		bookService.updateBook(bookDTO, token, bookId);
		return ResponseEntity.ok().body(new Response(HttpStatus.OK.value(),env.getProperty("2002")));
	}
	
	@ApiOperation(value = "Write Review of the book")
	@PutMapping("/ratingreview")
	public ResponseEntity<Response> writeReview(@RequestBody RatingReviewDTO rrDto,@RequestHeader(name="token") String token, @RequestParam Long bookId){
		bookService.writeReviewAndRating(token, rrDto, bookId);
		return ResponseEntity.ok().body(new Response(HttpStatus.OK.value(),env.getProperty("2004")));
	}
	
	@ApiOperation(value = "Delete a Book Details")
	@DeleteMapping("/delete")
	public ResponseEntity<Response> deleteBook(@RequestHeader(name="token") String token, @RequestParam Long bookId){
		bookService.deleteBook(token, bookId);
		return ResponseEntity.ok().body(new Response(HttpStatus.OK.value(),env.getProperty("2003")));
	}
	
	@ApiOperation(value = "Get verified Book Details")
	@GetMapping("/bookdetails")
	public ResponseEntity<Response> getBooks(@RequestParam Integer pageNo){
		return ResponseEntity.ok().body(new Response(HttpStatus.OK.value(),env.getProperty("3001"), bookService.getBooks(pageNo)));
	}
	
	@ApiOperation(value = "Get Book Details sorted by price in Low-High order")
	@GetMapping("/sortbylowprice")
	public ResponseEntity<Response> getBooksSortedByPriceLow(@RequestParam Integer pageNo){
		return ResponseEntity.ok().body(new Response(HttpStatus.OK.value(),env.getProperty("3001"), bookService.getBooksSortedByPriceLow(pageNo)));
	}
	
	@ApiOperation(value = "Get Book Details sorted by price in High-Low order")
	@GetMapping("/sortbyhighprice")
	public ResponseEntity<Response> getBooksSortedByPriceHigh(@RequestParam Integer pageNo){
		return ResponseEntity.ok().body(new Response(HttpStatus.OK.value(),env.getProperty("3001"), bookService.getBooksSortedByPriceHigh(pageNo)));
	}
	
	@ApiOperation(value = "Get Book Details sorted by arrival time")
	@GetMapping("/sortbyarrival")
	public ResponseEntity<Response> getBooksSortedByArrival(@RequestParam Integer pageNo){
		return ResponseEntity.ok().body(new Response(HttpStatus.OK.value(),env.getProperty("3001"), bookService.getBooksSortedByArrival(pageNo)));
	}
	
	@ApiOperation(value = "Get Book Details Name and Author")
	@GetMapping("/bookorauthorname")
	public ResponseEntity<Response> getBookByNameAndAuthor(@RequestParam String text){
		return ResponseEntity.ok().body(new Response(HttpStatus.OK.value(),env.getProperty("3001"), bookService.getBookByNameAndAuthor(text)));
	}
	
	@ApiOperation(value = "Get Book rating and review")
	@GetMapping("/ratingreviews")
	public ResponseEntity<Response> getBookRatingAndReview(@RequestParam Long bookId){
		return ResponseEntity.ok().body(new Response(HttpStatus.OK.value(),env.getProperty("3002"), bookService.getRatingsOfBook(bookId)));
	}
	
	@ApiOperation(value = "Upload book image")
	@PutMapping("/uploadbookimage")
    public ResponseEntity<Response> uploadProfile(@RequestPart(value = "file") MultipartFile file,@RequestHeader(name = "token") String token, @RequestParam Long bookId)
    {
		awsService.uploadFileToS3Bucket(file,token, bookId ,ImageType.BOOK);
        return ResponseEntity.ok().body(new Response(HttpStatus.OK.value(),"file [" + file.getOriginalFilename() + "] uploading request submitted successfully."));
    }
	@ApiOperation(value = "Get verified Book Count")
	@GetMapping("/bookscount/{get}")
	public ResponseEntity<Response> getBooksCount(@PathVariable String get){
		return ResponseEntity.ok().body(new Response(HttpStatus.OK.value(),env.getProperty("3001"), bookService.getBooksCount()));
	}
	
}
