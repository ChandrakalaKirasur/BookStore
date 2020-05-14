package com.bridgelabz.bookstoreapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
import com.bridgelabz.bookstoreapi.entity.OrderDetails;
import com.bridgelabz.bookstoreapi.entity.User;
import com.bridgelabz.bookstoreapi.response.Response;
import com.bridgelabz.bookstoreapi.response.UserResponse;
import com.bridgelabz.bookstoreapi.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/user")
@PropertySource("classpath:message.properties")
@CrossOrigin("*")
@Api(value="bookStore", description="Operations pertaining to user in Book Store")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private Environment env;

	
	/**
	 * API for user login
	 * @param RequestBody userlogin
	 */

	@ApiOperation(value = "User Login",response = Iterable.class)
	@PostMapping(value = "/login")
	public ResponseEntity<Response> loginUser(@Valid @RequestBody LoginDTO user, BindingResult result) {
//		if (result.hasErrors())
//			return ResponseEntity.status(401)
//					.body(new UserResponse(result.getAllErrors().get(0).getDefaultMessage(), "",HttpStatus.OK));
//		
		 String token = userService.loginByEmailOrMobile(user);
		
		 Response response = new Response(HttpStatus.OK.value(),"User loggedin successfully", token);
			System.out.println("token"+response.getStatus());
			return new ResponseEntity<>(response, HttpStatus.OK);
		
	}
	
	/**
	 * API for user registeration
	 * @param RequestBody register
	 */

	@ApiOperation(value = "register",response = Iterable.class)
	@PostMapping(value = "/registration")
	public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterDto userRecord, BindingResult result) {
		if (result.hasErrors())
			return ResponseEntity.status(401)
					.body(new UserResponse(result.getAllErrors().get(0).getDefaultMessage(), "",HttpStatus.BAD_REQUEST));
		User user = userService.userRegistration(userRecord);
		if (user != null) {
			return ResponseEntity.status(200)
					.body(new UserResponse(env.getProperty("200"), userRecord,HttpStatus.OK));
		}
		return ResponseEntity.status(401)
				.body(new UserResponse(env.getProperty("102"),  userRecord,HttpStatus.NOT_FOUND));
	}

	/**
	 * API for verifying the token User
	 * @param pathVaraiable token
	 */

	@ApiOperation(value = "verifing the user",response = Iterable.class)
	@GetMapping(value = "/registration/verify/{token}")
	public ResponseEntity<UserResponse> userVerify(@PathVariable("token") String token) throws Exception {
		
		boolean verification = userService.updateVerificationStatus(token);
		if (verification) {
			return ResponseEntity.status(200).body(new UserResponse(env.getProperty("201"), verification,HttpStatus.OK));
		}
		return ResponseEntity.status(401).body(new UserResponse(env.getProperty("104"), verification,HttpStatus.HTTP_VERSION_NOT_SUPPORTED));
	}
	
	/**
	 * API for verify resting password
	 * @param RequestParam emailId
	 */
	@PostMapping("/forgetPassword")
	public ResponseEntity<UserResponse> forgetPassword(@Valid @RequestParam String email) {
		String message = userService.forgotpassword(email);
		return ResponseEntity.status(200)
				.body(new UserResponse(env.getProperty("107"),message,HttpStatus.OK));
	}


	/**
	 * API for user Forgot Passsword
	 * @param pathVaraiable token
	 * @param RequestParam newpassword
	 */

	@PutMapping("/resetPassword/{token}")
	public ResponseEntity<UserResponse> restpassword(@Valid @PathVariable String token,
			@RequestBody sellerForgetPasswordDto forgetPasswordDto) {
		String message = userService.resetpassword(token, forgetPasswordDto);
		return ResponseEntity.status(200)
				.body(new UserResponse(message,env.getProperty("107"),HttpStatus.OK));
	}
	

	@ApiOperation(value = "getting the user")
	@GetMapping(value = "/get/{token}")
	public ResponseEntity<UserResponse> gettingUser(@RequestHeader String token) throws Exception {
		
		User userdetails = userService.getUser(token);
			return ResponseEntity.status(200).body(new UserResponse(env.getProperty("201"),userdetails,HttpStatus.OK));
		
	}
	
	
}
