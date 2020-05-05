package com.bridgelabz.bookstoreapi.service;

import java.util.List;

import com.bridgelabz.bookstoreapi.dto.AddressDto;
import com.bridgelabz.bookstoreapi.dto.UpdateAddressDto;
import com.bridgelabz.bookstoreapi.entity.Address;

public interface AddressService {

	boolean addAddress(AddressDto address, String token);

	Address deleteNote(String token, long addressId);

	List<Address> updateAddress(UpdateAddressDto address, String token);


}
