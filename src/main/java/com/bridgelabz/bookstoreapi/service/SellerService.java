package com.bridgelabz.bookstoreapi.service;

import java.util.Optional;

import javax.validation.Valid;

import com.bridgelabz.bookstoreapi.dto.LoginDTO;
import com.bridgelabz.bookstoreapi.dto.RegisterDto;
import com.bridgelabz.bookstoreapi.dto.sellerForgetPasswordDto;
import com.bridgelabz.bookstoreapi.entity.Seller;
import com.bridgelabz.bookstoreapi.response.Response;

public interface SellerService {
	/**
	 * @param register
	 * @return
	 */
	public boolean sellerRegistration(RegisterDto register);
	/**
	 * 
	 * @param login
	 * @return token
	 */
	public String loginByEmailOrMobile(LoginDTO login);
	/**
	 * 
	 * @param emailAddress
	 * @return
	 */
	public String forgotpassword(@Valid String emailAddress);
	/**
	 * 
	 * @param token
	 * @param forgetPasswordDto
	 * @return
	 */
	public String resetpassword(@Valid String token, sellerForgetPasswordDto forgetPasswordDto);
}
