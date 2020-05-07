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
	@PostMapping("/Registration")
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
	@PostMapping("/Login")
	public ResponseEntity<SellerResponse> sellerLogin(@RequestBody LoginDTO sellerLoginDto) {
		System.out.println("Enter into login api");
		String token = sellerService.loginByEmailOrMobile(sellerLoginDto);
		if (token!= null) {
			SellerResponse sellr = new SellerResponse( environment.getProperty("202"),200,token);
			return new ResponseEntity<>(sellr,HttpStatus.OK);
			
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new SellerResponse(environment.getProperty("104"), 404, token));
	}
	@PostMapping("/forgetPassword")
	public ResponseEntity<SellerResponse> forgetPassword(@Valid @RequestParam String emailAddress) {
		String message = sellerService.forgotpassword(emailAddress);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new SellerResponse(environment.getProperty("201"), 201, message));
	}

	@PostMapping("/restPassword/{token}")
	public ResponseEntity<SellerResponse> restpassword(@Valid @RequestHeader String token,
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
	
	@ApiOperation(value = "Verifing the user",response = Iterable.class)
	@GetMapping(value = "/registration/verify/{token}")
	public ResponseEntity<SellerResponse> sellerVerify(@PathVariable("token") String token) throws Exception {
		
		boolean verification = sellerService.updateVerificationStatus(token);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new SellerResponse(environment.getProperty("308"), 201, verification));
	}
}

