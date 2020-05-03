package com.bridgelabz.bookstoreapi.service;

import com.bridgelabz.bookstoreapi.dto.LoginDTO;
import com.bridgelabz.bookstoreapi.dto.RegisterDto;
import com.bridgelabz.bookstoreapi.response.Response;

public interface SellerService {

	public boolean sellerRegistration(RegisterDto register);
	public String loginByEmailOrMobile(LoginDTO login);
}
