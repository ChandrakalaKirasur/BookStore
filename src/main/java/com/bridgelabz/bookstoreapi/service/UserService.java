package com.bridgelabz.bookstoreapi.service;

import java.util.List;

import javax.validation.Valid;

import com.bridgelabz.bookstoreapi.dto.LoginDTO;
import com.bridgelabz.bookstoreapi.dto.RegisterDto;
import com.bridgelabz.bookstoreapi.dto.sellerForgetPasswordDto;
import com.bridgelabz.bookstoreapi.entity.OrderDetails;
import com.bridgelabz.bookstoreapi.entity.User;
import com.bridgelabz.bookstoreapi.response.UserResponse;

public interface UserService {

	User userRegistration(RegisterDto register);
	
	String loginByEmailOrMobile(LoginDTO login);

	boolean updateVerificationStatus(String token);

	String forgotpassword(@Valid String email);

	String resetpassword(@Valid String token, sellerForgetPasswordDto forgetPasswordDto);

	User getUser(String token);

	String getUserProfile(String token);

	
	
}
