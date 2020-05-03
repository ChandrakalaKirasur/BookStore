package com.bridgelabz.bookstoreapi.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.bookstoreapi.dto.LoginDTO;
import com.bridgelabz.bookstoreapi.dto.RegisterDto;
import com.bridgelabz.bookstoreapi.entity.User;
import com.bridgelabz.bookstoreapi.response.UserResponse;
import com.bridgelabz.bookstoreapi.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/users")
@PropertySource("classpath:message.properties")
@CrossOrigin("*")
@Api(value="bookStore", description="Operations pertaining to user in Online Store")
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
	public ResponseEntity<UserResponse> loginUser(@Valid @RequestBody LoginDTO user, BindingResult result) {
		if (result.hasErrors())
			return ResponseEntity.status(401)
					.body(new UserResponse(result.getAllErrors().get(0).getDefaultMessage(), "200"));
		
		 UserResponse userdetails = userService.loginByEmailOrMobile(user);
		
		return  ResponseEntity.status(200).body(userdetails);
		
	}
	
	/**
	 * API for user registeration
	 * @param RequestBody register
	 */

	@ApiOperation(value = "User Registerartion",response = Iterable.class)
	@PostMapping(value = "/add-user")
	public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterDto userRecord, BindingResult result) {
		if (result.hasErrors())
			return ResponseEntity.status(401)
					.body(new UserResponse(result.getAllErrors().get(0).getDefaultMessage(), "200"));
		User user = userService.userRegistration(userRecord);
		if (user != null) {
			return ResponseEntity.status(200)
					.body(new UserResponse(env.getProperty("200"), "200-ok", userRecord));
		}
		return ResponseEntity.status(401)
				.body(new UserResponse(env.getProperty("102"), "", userRecord));
	}

	/**
	 * API for verify resting password
	 * @param RequestParam emailId
	 */
//	@ApiOperation(value = "Reset the user password",response = Iterable.class)
//	@PostMapping(value = "/emailId")
//	public ResponseEntity<UserResponse> resetEmailVerify(@RequestParam("email") String emailId) {
//
//		String result = userService.resetPassword(emailId);
//	
//		return ResponseEntity.status(200)
//				.body(new UserResponse(result,env.getProperty("107"),200));
//		
//	}

	/**
	 * API for user Forgot Passsword
	 * @param pathVaraiable token
	 * @param RequestParam newpassword
	 */
//	@ApiOperation(value = "Forgot password",response = Iterable.class)
//	@PutMapping(value = "/forgotPassword/{token}")
//	public ResponseEntity<UserResponse> forgetPassword(@PathVariable String token,@RequestParam("newPassword") String rest) {
//
//		User result = userService.forgotPassword(rest,token);
//
//		return ResponseEntity.status(HttpStatus.ACCEPTED)
//					.body(new UserResponse(env.getProperty("108"), "200-ok", result));
//		
//	}

	/**
	 * API for verifying the token User
	 * @param pathVaraiable token
	 */

	@ApiOperation(value = "Verifing the user",response = Iterable.class)
	@GetMapping(value = "/registration/verify/{token}")
	public ResponseEntity<UserResponse> userVerify(@PathVariable("token") String token) throws Exception {
		
		boolean verification = userService.updateVerificationStatus(token);
		if (verification) {
			return ResponseEntity.status(200).body(new UserResponse(env.getProperty("201"), "200"));
		}
		return ResponseEntity.status(401).body(new UserResponse(env.getProperty("104"), "401"));
	}

}
