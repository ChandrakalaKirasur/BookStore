package com.bridgelabz.bookstoreapi.service;

cvk
import java.util.List;
import java.util.Optional;

master
import javax.validation.Valid;

import com.bridgelabz.bookstoreapi.dto.LoginDTO;
import com.bridgelabz.bookstoreapi.dto.RegisterDto;
import com.bridgelabz.bookstoreapi.dto.sellerForgetPasswordDto;

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
}
