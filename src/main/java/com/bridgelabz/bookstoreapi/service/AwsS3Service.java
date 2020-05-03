package com.bridgelabz.bookstoreapi.service;

import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.bookstoreapi.utility.ImageType;

public interface AwsS3Service {

	public void uploadFileToS3Bucket(MultipartFile multipartFile, boolean enablePublicReadAccess, Long token, ImageType type);
}
