package com.bridgelabz.bookstoreapi.service;


import java.util.List;


import javax.validation.Valid;

import com.bridgelabz.bookstoreapi.dto.LoginDTO;
import com.bridgelabz.bookstoreapi.dto.RegisterDto;
import com.bridgelabz.bookstoreapi.dto.sellerForgetPasswordDto;

import com.bridgelabz.bookstoreapi.entity.Seller;


public interface SellerService {
	/**
	 * @param register
	 * @return
	 */
	public Seller sellerRegistration(RegisterDto register);
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
	public String forgotpassword(@Valid String email);
	/**
	 * 
	 * @param token
	 * @param forgetPasswordDto
	 * @return
	 */
	public String resetpassword(@Valid String token, sellerForgetPasswordDto forgetPasswordDto);
	/**
	 * 
	 * @return all sellers
	 */
	public List<Seller> getSellers();
	/**
	 * 
	 * @param token
	 * @return 
	 */
	public Seller getSingleUser(String token);
	/**
	 * 
	 * @param token
	 * @return
	 */
	public boolean updateVerificationStatus(String token);
}
