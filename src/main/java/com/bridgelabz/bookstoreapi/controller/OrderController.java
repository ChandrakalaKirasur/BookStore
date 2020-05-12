package com.bridgelabz.bookstoreapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bookstoreapi.entity.OrderDetails;
import com.bridgelabz.bookstoreapi.entity.User;
import com.bridgelabz.bookstoreapi.response.UserResponse;
import com.bridgelabz.bookstoreapi.service.OrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/orders")
@PropertySource("classpath:message.properties")
@CrossOrigin("*")
@Api(value="bookStore", description="Operations pertaining to orderDetails in Book Store")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private Environment env;
	
	@ApiOperation(value = "Getting the confrim order of user")
	@PutMapping(value = "/getOrderConfrim/{token}")
	public ResponseEntity<UserResponse> OrderConfrim(@RequestHeader String token) throws Exception {
		
		 List<OrderDetails> userdetails = orderService.getOrderConfrim(token);
			return ResponseEntity.status(200).body(new UserResponse(env.getProperty("700"), userdetails,HttpStatus.OK));
		
	}
	
	@ApiOperation(value = "Getting the OrderList")
	@GetMapping(value = "/orderBooks/{token}")
	public ResponseEntity<UserResponse> getOrderlist(@RequestHeader String token) throws Exception {
		
		List<OrderDetails> userdetails = orderService.getOrderList(token);
			return ResponseEntity.status(200).body(new UserResponse(env.getProperty("700"),userdetails,HttpStatus.OK));
		
	}
}
