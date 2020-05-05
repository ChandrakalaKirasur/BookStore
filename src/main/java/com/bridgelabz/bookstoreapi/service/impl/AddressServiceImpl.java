package com.bridgelabz.bookstoreapi.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.bookstoreapi.dto.AddressDto;
import com.bridgelabz.bookstoreapi.dto.UpdateAddressDto;
import com.bridgelabz.bookstoreapi.entity.Address;
import com.bridgelabz.bookstoreapi.entity.User;
import com.bridgelabz.bookstoreapi.exception.UserException;
import com.bridgelabz.bookstoreapi.repository.AddressRepository;
import com.bridgelabz.bookstoreapi.service.AddressService;
import com.bridgelabz.bookstoreapi.utility.JWTUtil;
@Service
public class AddressServiceImpl implements AddressService{

	@Autowired
	private JWTUtil jwt;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private Environment env;

	@Transactional
	@Override
	public boolean addAddress(AddressDto address,String token) {
		Long uId = jwt.decodeToken(token);
		User user=addressRepository.findUserById(uId);
		Address add=new Address();
		BeanUtils.copyProperties(address,add);
		add.setAddress(address.getAddress());
		add.setType(address.getType());
		add.setCity(address.getCity());
		add.setCountry(address.getCountry());
		add.setHouseNo(address.getHouseNo());
		add.setLandmark(address.getLandmark());
		add.setPincode(address.getPincode());
		add.setState(address.getState());
		add.setStreet(address.getStreet());
		addressRepository.save(add);
		return user.getAddress().add(add);
	}

	@Override
	public Address deleteNote(String token, long addressId) {

		Address address=new Address();
		Long uId = jwt.decodeToken(token);
		User user =addressRepository.findUserById(uId);
		List<Address> add = addressRepository.findAddressByUserId(addressId);

		addressRepository.delete(address);
		return address;



	}

	@Override
	public List<Address> updateAddress(UpdateAddressDto addressupdate, String token) {
		List<Address> list=new ArrayList<>();

		Long uId = jwt.decodeToken(token);
		User user=addressRepository.findUserById(uId);
		Optional<Address> noteInformation= addressRepository.findById(addressupdate.getAddressId());
		return noteInformation.filter(note -> {
			return note != null;
		}).map(add->{
			add.setAddressId((addressupdate.getAddressId()));
			add.setAddress(addressupdate.getAddress());
			add.setType(addressupdate.getType());
			add.setCity(addressupdate.getCity());
			add.setCountry(addressupdate.getCountry());
			add.setHouseNo(addressupdate.getHouseNo());
			add.setLandmark(addressupdate.getLandmark());
			add.setPincode(addressupdate.getPincode());
			add.setState(addressupdate.getState());
			add.setStreet(addressupdate.getStreet());
			addressRepository.save(add);
			user.getAddress().add(add);
			return list;
		}).orElseThrow(()-> new UserException(400, env.getProperty("104")));
	}
}





