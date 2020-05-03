package com.bridgelabz.bookstoreapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.bookstoreapi.dto.BookDTO;
import com.bridgelabz.bookstoreapi.response.Response;
import com.bridgelabz.bookstoreapi.service.AwsS3Service;
import com.bridgelabz.bookstoreapi.service.BookService;
import com.bridgelabz.bookstoreapi.utility.ImageType;

@RestController
@RequestMapping("/book")
@CrossOrigin
@PropertySource("classpath:message.properties")
public class BookController {

	@Autowired
	private BookService bookService;
	
	@Autowired
	private AwsS3Service awsService;
	
	@Autowired
	private Environment env;
	
	@PostMapping("/add")
	public ResponseEntity<Response> addBook(@RequestBody BookDTO bookDTO,@RequestHeader(name="token") String token){
		bookService.addBook(bookDTO, token);
		return ResponseEntity.ok().body(new Response(HttpStatus.OK.value(),env.getProperty("2001")));
	}
	
	@PutMapping("/uploadProfile")
    public ResponseEntity<Response> uploadProfile(@RequestPart(value = "file") MultipartFile file,@RequestHeader(name = "token") Long token)
    {
		awsService.uploadFileToS3Bucket(file, true,token,ImageType.BOOK);
        return ResponseEntity.ok().body(new Response(HttpStatus.OK.value(),"file [" + file.getOriginalFilename() + "] uploading request submitted successfully."));
    }
	
	
}
