package com.bridgelabz.bookstoreapi.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.bridgelabz.bookstoreapi.dto.AdminDto;
import com.bridgelabz.bookstoreapi.dto.AdminPasswordResetDto;
import com.bridgelabz.bookstoreapi.dto.LoginDTO;
import com.bridgelabz.bookstoreapi.entity.Admin;
import com.bridgelabz.bookstoreapi.response.AdminResponse;
import com.bridgelabz.bookstoreapi.service.AdminService;
import com.bridgelabz.bookstoreapi.utility.JWTUtil;
import com.bridgelabz.bookstoreapi.utility.Token;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {
	@Autowired
	private AdminService service;
	@Autowired
	private JWTUtil util;
	@Autowired
	private Environment env;
	@GetMapping("/")
	public String welcome() {
		return "welcome ";
	}
	
	/**
	 * API for admin registration
	 * @param RequestBody register
	 */
	@ApiOperation(value = "Admin Registerartion",response = Iterable.class)
	@PostMapping("/registration")
	public ResponseEntity<AdminResponse> register(@Valid @RequestBody AdminDto register, BindingResult result) {
		if (result.hasErrors())
			return ResponseEntity.status(401)
					.body(new AdminResponse(result.getAllErrors().get(0).getDefaultMessage(), 401,""));
		boolean resultStatus = service.register(register);
		if (resultStatus) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new AdminResponse(env.getProperty("800"), 200, resultStatus));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new AdminResponse(env.getProperty("802"), 208, resultStatus));
	}
	
	/**
	 * API for verifying the Admin email 
	 * @param pathVaraiable token
	 */
	@ApiOperation(value = "Verifing the admin",response = Iterable.class)
	@GetMapping("/registration/verify/{token}")
	public ResponseEntity<AdminResponse> verifyMail(@PathVariable String token) {
		boolean resultStatus = service.verifyEmail(token);
		if (resultStatus) {
			return ResponseEntity.status(HttpStatus.OK).body(new AdminResponse("805", 200, resultStatus));
		}
		return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT)
				.body(new AdminResponse(env.getProperty("806"), 208, resultStatus));
	}
	
	/**
	 * API for admin login
	 * @param RequestBody adminLoginDto
	 */
	@ApiOperation(value = "Admin Login",response = Iterable.class)
	@PostMapping("/login")
	public ResponseEntity<AdminResponse> login(@Valid @RequestBody LoginDTO adminLoginDto,BindingResult result) {
		if (result.hasErrors())
			return ResponseEntity.status(401)
					.body(new AdminResponse(result.getAllErrors().get(0).getDefaultMessage(), 401,""));
		else {
			Admin admin = service.login(adminLoginDto);
			if (admin != null) {
				String token=util.generateToken(admin.getAdminId(),Token.WITH_EXPIRE_TIME);
				return ResponseEntity.status(HttpStatus.OK).body(new AdminResponse(env.getProperty("801"), 202, token));
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new AdminResponse("Admin name or password is invalid ", 208,""));
		}
	}
	/**
	 * API for admin to forgot password 
	 * @param RequestParam email
	 */
	@ApiOperation(value = "Admin forgot password",response = Iterable.class)
	@PostMapping("/forgetPassword")
	public ResponseEntity<AdminResponse> sendLinkToResetPassword(@RequestParam String email) {
		boolean resultStatus = service.sendLinkForPassword(email);
		if (resultStatus) {
			return ResponseEntity.status(HttpStatus.OK).body(new AdminResponse(env.getProperty("403"), 200, resultStatus));
		}
		return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT)
				.body(new AdminResponse(env.getProperty("806"), 208, resultStatus));
	}
	/**
	 * API for admin to reset password 
	 * @param RequestBody resetDetails
	 */
	@ApiOperation(value = "Admin reset password",response = Iterable.class)
	@PutMapping("/resetPassword/{token}")
	public ResponseEntity<AdminResponse> resetAdminPassword(@RequestBody AdminPasswordResetDto resetDetails,@PathVariable String token){
		boolean resultStatus = service.resetAdminPassword(resetDetails,token);
		if (resultStatus) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new AdminResponse(env.getProperty("203"), 200, resultStatus));
		}
		return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT)
				.body(new AdminResponse(env.getProperty("701"), 208, resultStatus));
	}
	/**
	 * API for verifying book
	 * @param PathVariable book token and @RequestHeader as admin token
	 */
	@ApiOperation(value = "Verify seller book",response = Iterable.class)
	@PutMapping("/verify/book/{booktoken}")
	public ResponseEntity<AdminResponse> verifyBook(@PathVariable String booktoken,@RequestHeader String token){
		boolean resultStatus = service.verifyBook(booktoken,token);
		if (resultStatus) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new AdminResponse("book verification successful", 200, resultStatus));
		}
		return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT)
				.body(new AdminResponse("Got error while verifying book!! try again", 208, resultStatus));
	}
}
