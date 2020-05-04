package com.bridgelabz.bookstoreapi.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bookstoreapi.dto.LoginDTO;
import com.bridgelabz.bookstoreapi.dto.RegisterDto;
import com.bridgelabz.bookstoreapi.dto.sellerForgetPasswordDto;
import com.bridgelabz.bookstoreapi.response.Response;
import com.bridgelabz.bookstoreapi.response.SellerResponse;
import com.bridgelabz.bookstoreapi.service.SellerService;

@RestController
public class SellerController {
	private SellerService sellerService;
	@Autowired
	private Environment environment;

	/** 
	 * @param iSellerService
	 * @param iJwtGenerator
	 */
	@Autowired
	public SellerController(SellerService iSellerService) {
		this.sellerService=iSellerService;
	}
	/**
	 * Api for Registration 
	 * @param information
	 * @return
	 * @throws Exception
	 */
	@PostMapping("seller/Registration")
	public ResponseEntity<SellerResponse> registration(@RequestBody RegisterDto sellerRegistrationDto) throws Exception {
		System.out.println(sellerRegistrationDto.getEmailAddress());
		boolean reg = sellerService.sellerRegistration(sellerRegistrationDto);
		if (reg) {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new SellerResponse(environment.getProperty("201"), 201, sellerRegistrationDto));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new SellerResponse(environment.getProperty("400"), 400, sellerRegistrationDto));
	}
	/**
	 *  Api for Registration 
	 *  @RequestBody-UserLoginDetails
	 */
	@PostMapping("seller/Login")
	public ResponseEntity<SellerResponse> sellerLogin(@RequestBody LoginDTO sellerLoginDto) {
		System.out.println("Enter into login api");
		String token = sellerService.loginByEmailOrMobile(sellerLoginDto);
		if (token!= null) {
			SellerResponse sellr = new SellerResponse( environment.getProperty("202"),200,token);
			return new ResponseEntity<>(sellr,HttpStatus.OK);
			
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new SellerResponse(environment.getProperty("104"), 404, token));
	}
	@PostMapping("seller/forgetPassword")
	public ResponseEntity<SellerResponse> forgetPassword(@Valid @RequestParam String emailAddress) {
		String message = sellerService.forgotpassword(emailAddress);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new SellerResponse(environment.getProperty("201"), 201, message));
	}

	@PostMapping("seller/restPassword/{token}")
	public ResponseEntity<SellerResponse> restpassword(@Valid @RequestHeader String token,
			@RequestBody sellerForgetPasswordDto forgetPasswordDto) {
		String messege = sellerService.resetpassword(token, forgetPasswordDto);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new SellerResponse(environment.getProperty("201"), 201, messege));
	}
}

