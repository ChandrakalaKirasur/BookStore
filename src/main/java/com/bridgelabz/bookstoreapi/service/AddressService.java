package com.bridgelabz.bookstoreapi.service;

import java.util.List;
import java.util.Optional;

import com.bridgelabz.bookstoreapi.dto.AddressDto;
import com.bridgelabz.bookstoreapi.dto.UpdateAddressDto;
import com.bridgelabz.bookstoreapi.entity.Address;
import com.bridgelabz.bookstoreapi.entity.User;

public interface AddressService {

	/**
	 * 
	 * @param address
	 * @param token
	 * @return address
	 */
	Address addAddress(AddressDto address, String token);
	/**
	 * 
	 * @param token
	 * @param addressId
	 * @return message
	 */
	User deleteAddress(String token, Long addressId);
	/**
	 * 
	 * @param address
	 * @param token
	 * @return address
	 */
	Optional<Address> updateAddress(UpdateAddressDto address, String token);
	/**
	 * 
	 * @return List<Address>
	 */
	List<Address> getAllAddress();
	/**
	 * 
	 * @param id
	 * @return adress
	 */
	Address getAddress(Long id);
	/**
	 * 
	 * @param token
	 * @return Address
	 */
	List<Address> getAddressByUserId(String token);
	/**
	 * 
	 * @param home
	 * @param token 
	 * @return
	 */
	Address getAddress(String home, String token);


}
