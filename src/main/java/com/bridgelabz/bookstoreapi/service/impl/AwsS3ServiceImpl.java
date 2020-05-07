package com.bridgelabz.bookstoreapi.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
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
import com.bridgelabz.bookstoreapi.constants.Constants;
import com.bridgelabz.bookstoreapi.entity.Book;
import com.bridgelabz.bookstoreapi.entity.Seller;
import com.bridgelabz.bookstoreapi.entity.User;
import com.bridgelabz.bookstoreapi.exception.BookException;
import com.bridgelabz.bookstoreapi.exception.SellerException;
import com.bridgelabz.bookstoreapi.exception.UserException;
import com.bridgelabz.bookstoreapi.repository.BookRepository;
import com.bridgelabz.bookstoreapi.repository.SellerRepository;
import com.bridgelabz.bookstoreapi.repository.UserRepository;
import com.bridgelabz.bookstoreapi.service.AwsS3Service;
import com.bridgelabz.bookstoreapi.utility.ImageType;
import com.bridgelabz.bookstoreapi.utility.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@PropertySource("classpath:message.properties")
public class AwsS3ServiceImpl implements AwsS3Service {

	@Value("${aws.bucket.name}")
	private String bucketName;

	@Autowired
	private AmazonS3 amazonS3Client;

	@Autowired
	private JWTUtil jwt;

	@Autowired
	private Environment env;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private SellerRepository sellerRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RestHighLevelClient client;

	@Autowired
	private ObjectMapper objectMapper;
	
//	@Autowired
//	private AdminRepository adminRepository;

	@Async
	public void uploadFileToS3Bucket(MultipartFile multipartFile, String token, Long bookId, ImageType type) {
		String fileName = multipartFile.getOriginalFilename();
		String fullName = fileName(type, fileName);

		try {
			// creating the file in the server (temporarily)
			File file = new File(fileName);
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(multipartFile.getBytes());
			fos.close();
			PutObjectRequest putObjectRequest = null;

			putObjectRequest = new PutObjectRequest(this.bucketName, fullName, file);

			putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);

			this.amazonS3Client.putObject(putObjectRequest);
			// removing the file created in the server
			file.delete();
			fetchObjectURL(token, bookId, fullName, type);
		} catch (IOException | AmazonServiceException ex) {
			// log.error("error [" + ex.getMessage() + "] occurred while uploading [" +
			// fileName + "] ");
		}
	}

	@Transactional
	private void fetchObjectURL(String token, Long bookId, String key, ImageType type) {

		Long id = jwt.decodeToken(token);
		
		if (type.equals(ImageType.BOOK)) {
			
			Seller seller = sellerRepository.findById(id)
					.orElseThrow(() -> new SellerException(404, env.getProperty("104")));
			List<Book> books = seller.getSellerBooks();
			Book filteredBook = books.stream().filter(book -> book.getBookId().equals(bookId)).findFirst()
					.orElseThrow(() -> new BookException(404, env.getProperty("4041")));
			filteredBook.setBookImage(getImageUrl(key));
			Book updatedBook = bookRepository.save(filteredBook);
			sellerRepository.save(seller);
			if(updatedBook.isBookVerified())
				updateBookInES(updatedBook);
		}
		else if(type.equals(ImageType.SELLER)) {
			Seller seller = sellerRepository.findById(id)
					.orElseThrow(() -> new SellerException(404, env.getProperty("104")));
//			seller.setSellerProfilePic(getImageUrl(key));
			sellerRepository.save(seller);
		}
		else if (type.equals(ImageType.USER)) {
			User user = userRepository.findById(id)
					.orElseThrow(() -> new UserException(404, env.getProperty("104")));
			user.setProfile(getImageUrl(key));
			userRepository.save(user);
		}
		else if (type.equals(ImageType.ADMIN)) {
//			Admin admin = adminRepository.findById(id)
//					.orElseThrow(() -> new AdminException(404, env.getProperty("104")));
//			admin.setProfile(getImageUrl(key));
//			adminRepository.save(user);
		}
	}

	private String getImageUrl(String key) {
		GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, key);
		URL url = amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest);
		String[] img = url.toString().split("\\?");
		return img[0];
	}

	private String fileName(ImageType type, String fileName) {
		String fullName = "";
		if (type.equals(ImageType.BOOK)) {
			fullName = "book" + '/' + fileName;
		} else if (type.equals(ImageType.USER)) {
			fullName = "user" + '/' + fileName;
		} else if (type.equals(ImageType.SELLER)) {
			fullName = "seller" + '/' + fileName;
		} else if (type.equals(ImageType.ADMIN)) {
			fullName = "admin" + '/' + fileName;
		}
		return fullName;
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
