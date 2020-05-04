package com.bridgelabz.bookstoreapi.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.bridgelabz.bookstoreapi.entity.Book;
import com.bridgelabz.bookstoreapi.entity.Seller;
import com.bridgelabz.bookstoreapi.exception.BookException;
import com.bridgelabz.bookstoreapi.exception.SellerException;
import com.bridgelabz.bookstoreapi.repository.BookRepository;
import com.bridgelabz.bookstoreapi.repository.SellerRepository;
import com.bridgelabz.bookstoreapi.service.AwsS3Service;
import com.bridgelabz.bookstoreapi.utility.ImageType;
import com.bridgelabz.bookstoreapi.utility.JWTUtil;

@Service
@PropertySource("classpath:message.properties")
public class AwsS3ServiceImpl implements AwsS3Service{

	@Value("${aws.bucket.name}")
    private String bucketName;

    @Autowired
    private  AmazonS3 amazonS3Client;
    
    @Autowired
	private JWTUtil jwt;
    
    @Autowired
	private Environment env;
	
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private SellerRepository sellerRepository;
    
    @Async
    public void uploadFileToS3Bucket(MultipartFile multipartFile, String token, Long bookId, ImageType type) 
    {
        String fileName = multipartFile.getOriginalFilename();
        
        try {
            //creating the file in the server (temporarily)
            File file = new File(fileName);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(multipartFile.getBytes());
            fos.close();
            PutObjectRequest putObjectRequest = null;
         
            putObjectRequest = new PutObjectRequest(this.bucketName, "book"+'/'+fileName, file);
           
            putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
            
            this.amazonS3Client.putObject(putObjectRequest);
            //removing the file created in the server
            file.delete();
            fetchObjectURL(token, bookId , "book"+'/'+fileName);
        } catch (IOException | AmazonServiceException ex) {
           // log.error("error [" + ex.getMessage() + "] occurred while uploading [" + fileName + "] ");
        }
    }
    
    @Transactional
    private void fetchObjectURL(String token, Long bookId, String key) {
    
    	Long sId = jwt.decodeToken(token);
    	Seller seller = sellerRepository.findById(sId).orElseThrow(() -> new SellerException(404, env.getProperty("104")));
		List<Book> books = seller.getSellerBooks();
		Book filteredBook = books.stream().filter(book -> book.getBookId().equals(bookId)).findFirst()
				.orElseThrow(() -> new BookException(404, env.getProperty("4041")));
//    	Book filteredBook = bookRepository.findById(bookId).orElseThrow(() -> new BookException(404, env.getProperty("4041")));
    	GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, key);
    	URL url=amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest);
    	String[] img = url.toString().split("\\?");

    	filteredBook.setBookImage(img[0]);
    	bookRepository.save(filteredBook);
    	sellerRepository.save(seller);
    }
}
