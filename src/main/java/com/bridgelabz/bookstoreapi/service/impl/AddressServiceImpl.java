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
import com.bridgelabz.bookstoreapi.exception.AddressException;
import com.bridgelabz.bookstoreapi.exception.UserException;
import com.bridgelabz.bookstoreapi.repository.AddressRepository;
import com.bridgelabz.bookstoreapi.repository.UserRepository;
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
	@Autowired
	private UserRepository userRepository;

	@Transactional
	@Override
	public Address addAddress(AddressDto address,String token) {
		Long uId = jwt.decodeToken(token);
		Address add=new Address( address);
		System.out.println(add.getAddress()+"->"+add.getCity());
		User userdetails = userRepository.findById(uId)
				.orElseThrow(()->new UserException(400, env.getProperty("104")));
//		BeanUtils.copyProperties(address,Address.class);
		userdetails.getAddress().add(add);
		return   addressRepository.save(add);

	}
	@Transactional
	@Override
	public User deleteAddress(String token, Long addressId) {

		Long uId = jwt.decodeToken(token);
		User userdetails = userRepository.findById(uId)
				.orElseThrow(()->new UserException(400, env.getProperty("104")));
		List<Address> deleteaddress = getAllAddress();
		Address filteredaddress = deleteaddress.stream().filter(address -> address.getAddressId().equals(addressId)).findFirst()
				.orElseThrow(() -> new AddressException(404, env.getProperty("4041")));
		deleteaddress.remove(filteredaddress);
		addressRepository.delete(filteredaddress);
		return userRepository.save(userdetails);



	}
	@Transactional
	@Override
	public Optional<Address> updateAddress(UpdateAddressDto addressupdate, String token) {
		List<Address> list=new ArrayList<>();

		Long uId = jwt.decodeToken(token);
		User userdetails = userRepository.findById(uId)
				.orElseThrow(()->new UserException(400, env.getProperty("104")));
		Optional<Address> ad= addressRepository.findById(addressupdate.getAddressId());
		return ad.filter(note -> {
			return note != null;
		}).map(add->{
			add.setAddressId((addressupdate.getAddressId()));
			add.setAddress(addressupdate.getAddress());
			add.setType(addressupdate.getType());
			add.setCity(addressupdate.getCity());
			add.setCountry(addressupdate.getCountry());
			add.setLandmark(addressupdate.getLandmark());
			add.setPincode(addressupdate.getPincode());
			add.setState(addressupdate.getState());
			
			addressRepository.save(add);
			userdetails.getAddress().add(add);
			return ad;
		}).orElseThrow(()-> new UserException(400, env.getProperty("104")));
	}
	@Transactional
	@Override
	public List<Address> getAllAddress() {
		List<Address> addList=new ArrayList<>();
		addressRepository.findAll().forEach(addList::add);
		return addList;
	}
	@Override
	public Address getAddress(Long id) {
		Address add=addressRepository.findAddressById(id);
		return add;
	}
	@Override
	public List<Address> getAddressByUserId(String token) {

		Long uId = jwt.decodeToken(token);
		User userdetails = userRepository.findById(uId)
				.orElseThrow(()->new UserException(400, env.getProperty("104")));

		try {
			List<Address> user = addressRepository.findAddressByUserId(uId);
			if (user != null) {
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public Address getAddress(String type,String token) {
		Long uId = jwt.decodeToken(token);
		User userdetails = userRepository.findById(uId)
				.orElseThrow(()->new UserException(400, env.getProperty("104")));
		Address add=addressRepository.findAddressBytext(uId,type);
		return add;
	}
}






