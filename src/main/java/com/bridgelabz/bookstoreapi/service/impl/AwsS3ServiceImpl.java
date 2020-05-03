package com.bridgelabz.bookstoreapi.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

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
import com.bridgelabz.bookstoreapi.exception.BookException;
import com.bridgelabz.bookstoreapi.repository.BookRepository;
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
    
    @Async
    public void uploadFileToS3Bucket(MultipartFile multipartFile, boolean enablePublicReadAccess, Long token, ImageType type) 
    {
//    	Long bId = jwt.decodeToken(token);
    	Book book = bookRepository.findById(token).orElseThrow(() -> new BookException(404, env.getProperty("4041")));
        String fileName = multipartFile.getOriginalFilename();
        
        try {
            //creating the file in the server (temporarily)
            File file = new File(fileName);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(multipartFile.getBytes());
            fos.close();
            PutObjectRequest putObjectRequest = null;
         
            	putObjectRequest = new PutObjectRequest(this.bucketName, "book"+'/'+fileName, file);
           

            if (enablePublicReadAccess) {
                putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
            }
            this.amazonS3Client.putObject(putObjectRequest);
            //removing the file created in the server
            file.delete();
            fetchObjectURL(book, "book"+'/'+fileName);
        } catch (IOException | AmazonServiceException ex) {
           // log.error("error [" + ex.getMessage() + "] occurred while uploading [" + fileName + "] ");
        }
    }
    
    @Transactional
    private void fetchObjectURL(Book book, String key) {
    
    	GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, key);
    	URL url=amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest);
    	System.out.println(url.toString().length());
    	book.setBookImage(url.toString());
    	bookRepository.save(book);
    }
}
