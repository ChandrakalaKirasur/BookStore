package com.bridgelabz.bookstoreapi.controller;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bookstoreapi.dto.LoginDTO;
import com.bridgelabz.bookstoreapi.dto.RegisterDto;
import com.bridgelabz.bookstoreapi.dto.sellerForgetPasswordDto;
import com.bridgelabz.bookstoreapi.entity.Seller;
import com.bridgelabz.bookstoreapi.response.Response;
import com.bridgelabz.bookstoreapi.response.SellerResponse;
import com.bridgelabz.bookstoreapi.response.UserResponse;
import com.bridgelabz.bookstoreapi.service.SellerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "/seller")
@Api(value="bookStore", description="Operations pertaining to Seller in Book Store")
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
	@PostMapping("/registration")
	public ResponseEntity<SellerResponse> registration(@RequestBody RegisterDto sellerRegistrationDto) throws Exception {
		Seller seller = sellerService.sellerRegistration(sellerRegistrationDto);
		if (seller!=null) {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new SellerResponse(environment.getProperty("200"), 201, sellerRegistrationDto));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new SellerResponse(environment.getProperty("400"), 400, sellerRegistrationDto));
	}
	/**
	 * Api for Registration
	 * @param sellerLoginDto
	 * @return token
	 */
	@PostMapping("/login")
	public ResponseEntity<Response> sellerLogin(@RequestBody LoginDTO sellerLoginDto) {
		String token = sellerService.loginByEmailOrMobile(sellerLoginDto);
		if (token!= null) {
			Response response = new Response(HttpStatus.OK.value(),"User loggedin successfully", token);
			System.out.println("token"+response.getStatus());
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		return null;
		}
	/**
	 * 
	 * @param email
	 * @return link
	 */
	@PostMapping("/forgetPassword")
	public ResponseEntity<SellerResponse> forgetPassword(@Valid @RequestParam String email) {
		String message = sellerService.forgotpassword(email);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new SellerResponse(environment.getProperty("201"), 201, message));
	}
	/**
	 * 
	 * @param token
	 * @param forgetPasswordDto
	 * @return message
	 */
	@PutMapping("/resetPassword/{token}")
	public ResponseEntity<SellerResponse> restpassword(@Valid @PathVariable String token,
			@RequestBody sellerForgetPasswordDto forgetPasswordDto) {
		String messege = sellerService.resetpassword(token, forgetPasswordDto);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new SellerResponse(environment.getProperty("201"), 201, messege));
	}
	/**
	 * API for retrieving the all the Sellers 
	 * @return all sellers
	 */
	@GetMapping("/allSellers")
	public List<Seller> getAllSellers() {
		List<Seller> users = sellerService.getSellers();
		return  users;
	}
	/**
	 * API to get the single Seller information  
	 * @param token
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/singleSeller")
	public ResponseEntity<SellerResponse> singleUser(@RequestHeader("token") String token) throws Exception {
		Seller user = sellerService.getSingleUser(token);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new SellerResponse(environment.getProperty("201"), 202, user));
	}

	@ApiOperation(value = "Verifing the seller",response = Iterable.class)
	@GetMapping(value = "/registration/verify/{token}")
	public ResponseEntity<SellerResponse> sellerVerify(@PathVariable("token") String token) throws Exception {

		boolean verification = sellerService.updateVerificationStatus(token);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new SellerResponse(environment.getProperty("308"), 201, verification));
	}
}

