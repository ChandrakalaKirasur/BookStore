package com.bridgelabz.bookstoreapi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
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
	/**
	 * Adding address
	 * @param address
	 * @param token
	 * @return Address
	 * @throws Exception
	 */
	@PostMapping("/add")
	public  ResponseEntity<AddressResponse> addAddress(@RequestBody AddressDto address,@RequestHeader String token) throws Exception {


		Address addres= addressService.addAddress(address,token);

		if (addres != null) {
			return ResponseEntity.status(200)
					.body(new AddressResponse(environment.getProperty("300"), "300-ok", addres));
		}
		return ResponseEntity.status(401)
				.body(new AddressResponse(environment.getProperty("102"), "", addres));	

	}
	/**
	 * 
	 * @param token
	 * @param address
	 * @return updateaddress
	 */
	/*Api for  update*/
	@PutMapping("/update/{token}")
	public ResponseEntity<AddressResponse> updateAddress(@PathVariable("token") String token,@RequestBody UpdateAddressDto address)
	{

		Optional<Address> addres=addressService.updateAddress(address,token);
		if (addres != null) {
			return ResponseEntity.status(200)
					.body(new AddressResponse(environment.getProperty("301"), "301", addres));
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
	public ResponseEntity<AddressResponse> deleteAddress(@RequestParam Long addressId,@RequestHeader String token )
	{
		System.out.println("####");
		User message= addressService.deleteAddress(token, addressId);
		System.out.println("==="+message);
		if (message != null) {
			return ResponseEntity.status(200)
					.body(new AddressResponse(environment.getProperty("302"), "302", message));
		}
		return ResponseEntity.status(401)
				.body(new AddressResponse(environment.getProperty("102"), "", message));		
	}
	/**
	 * 
	 * @return List<Address>
	 */
	/*Api for fetching all address*/
	@GetMapping("/getAllAddress")
	public List<Address> getAllAddress()
	{
		return addressService.getAllAddress();

	}
	/**
	 * 
	 * @param id
	 * @return address
	 */
	@GetMapping(value = "/getAddress/{id}")
	public ResponseEntity<AddressResponse> getAddress(@PathVariable Long id) {
		Address add = addressService.getAddress(id);
		if (add != null) {
			return ResponseEntity.status(200)
					.body(new AddressResponse(environment.getProperty("304"), "304", add));
		}
		return ResponseEntity.status(401)
				.body(new AddressResponse(environment.getProperty("305"), "", add));		
	}
	@GetMapping(value = "/users/{token}")
	public List<Address> getAddressByUserId(@PathVariable String token) {
		List<Address> result = addressService.getAddressByUserId(token);
		System.out.println("-----------result"+result);
		if (result != null) {
			 return result;
		}
		return null;
	}
}

