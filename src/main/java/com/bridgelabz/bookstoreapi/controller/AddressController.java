package com.bridgelabz.bookstoreapi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
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

import com.bridgelabz.bookstoreapi.dto.AddressDto;
import com.bridgelabz.bookstoreapi.dto.UpdateAddressDto;
import com.bridgelabz.bookstoreapi.entity.Address;
import com.bridgelabz.bookstoreapi.entity.User;
import com.bridgelabz.bookstoreapi.response.AddressResponse;
import com.bridgelabz.bookstoreapi.service.AddressService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/address")
@PropertySource("classpath:message.properties")
@CrossOrigin("*")
@Api(value="bookStore", description="Operations pertaining to Address in Online Store")
public class AddressController {
	@Autowired
	private AddressService addressService;
	@Autowired
	private Environment environment;
	@PostMapping("/add")
	public  ResponseEntity<AddressResponse> addAddress(@RequestBody AddressDto address,@RequestHeader String token) throws Exception {
		
		 
		Address addres= addressService.addAddress(address,token);
			
		if (addres != null) {
			return ResponseEntity.status(200)
					.body(new AddressResponse(environment.getProperty("200"), "200-ok", addres));
		}
		 return ResponseEntity.status(401)
				.body(new AddressResponse(environment.getProperty("102"), "", addres));	
		
	}
	/*Api for  update*/
	@PutMapping("/update/{token}")
	public ResponseEntity<AddressResponse> updateAddress(@PathVariable("token") String token,@RequestBody UpdateAddressDto address)
	{
		
		Optional<Address> addres=addressService.updateAddress(address,token);
		if (addres != null) {
			return ResponseEntity.status(200)
					.body(new AddressResponse(environment.getProperty("200"), "200-ok", addres));
		}
		return ResponseEntity.status(401)
				.body(new AddressResponse(environment.getProperty("102"), "", addres));

	}
	/**
	 * 
	 * @param id
	 * @param token	
	 *
	 */
	/*Api for  delete*/
	@PutMapping("/delete")
	public ResponseEntity<AddressResponse> deletNote(@RequestParam Long addressId,@RequestHeader String token )
	{
		System.out.println("####");
		User message= addressService.deleteNote(token, addressId);
		System.out.println("==="+message);
		if (message != null) {
			return ResponseEntity.status(200)
					.body(new AddressResponse(environment.getProperty("200"), "200-ok", message));
		}
		return ResponseEntity.status(401)
				.body(new AddressResponse(environment.getProperty("102"), "", message));		
	}
	/*Api for fetching all notes*/
	@GetMapping("/getAllNotes")
	public List<Address> getAllAddress()
	{


		return addressService.getAllAddress();

	}
}
