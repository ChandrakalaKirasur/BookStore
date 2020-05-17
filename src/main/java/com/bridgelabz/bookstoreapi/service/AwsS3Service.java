package com.bridgelabz.bookstoreapi.service;

import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.bookstoreapi.utility.ImageType;

public interface AwsS3Service {

	 public void uploadFileToS3Bucket(MultipartFile multipartFile, String token, Long bookId, ImageType type);

	public String uploadFileForUser(MultipartFile file, String token, ImageType user);
}
