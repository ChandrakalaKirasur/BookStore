package com.bridgelabz.bookstoreapi.service.impl;


import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.bridgelabz.bookstoreapi.configuration.Consumer;
import com.bridgelabz.bookstoreapi.configuration.Producer;
import com.bridgelabz.bookstoreapi.constants.Constants;
import com.bridgelabz.bookstoreapi.dto.LoginDTO;
import com.bridgelabz.bookstoreapi.dto.Mail;
import com.bridgelabz.bookstoreapi.dto.RegisterDto;
import com.bridgelabz.bookstoreapi.dto.sellerForgetPasswordDto;
import com.bridgelabz.bookstoreapi.entity.Seller;
import com.bridgelabz.bookstoreapi.exception.SellerException;
import com.bridgelabz.bookstoreapi.repository.SellerRepository;
import com.bridgelabz.bookstoreapi.service.SellerService;
import com.bridgelabz.bookstoreapi.utility.JWTUtil;
import com.bridgelabz.bookstoreapi.utility.Token;

@Service
@PropertySource("classpath:message.properties")
public class SellerServiceImpl implements SellerService{

	@Autowired
	private SellerRepository sellerRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	private Producer producer;
	
	@Autowired
	private Consumer consumer;
	
	@Autowired
	private JWTUtil jwt;
	
	@Autowired
	private Environment env;
	Seller seller =new Seller();
	
	/**
	 * Saves the user details
	 * @param register
	 * @Return 
	 */
	@Transactional
	@Override
	public boolean sellerRegistration(RegisterDto register) {

		if (sellerRepository.findByEmailAddress(register.getEmailAddress()).isPresent())
			//throw new SellerException(HttpStatus.FOUND.value(), env.getProperty("103"));

		register.setPassword(encoder.encode(register.getPassword()));
		System.out.println("password--->"+register.getPassword());
		Seller seller = new Seller(register);
		Mail mail = new Mail();
		try {
			String password=encoder.encode(register.getPassword());
			seller.setPassword(password);
			seller.setVerificationStatus(true);
			Seller sellr = sellerRepository.save(seller);
			if (sellr != null) {
				mail.setTo(seller.getEmailAddress());
				mail.setSubject(Constants.REGISTRATION_STATUS);
				mail.setContext("Hi " + seller.getSellerName() + " " + Constants.REGISTRATION_MESSAGE
						+ Constants.VERIFICATION_LINK + jwt.generateToken(seller.getSellerId(), Token.WITH_EXPIRE_TIME));
				producer.sendToQueue(mail);
				consumer.receiveMail(mail);
			}
		} catch (SellerException e) {
			throw new SellerException(400, env.getProperty("102"));
		}
		return false;
	}


	
	/**
	 * Login to the application using login credential
	 * @return user details which is necessary
	 * @param login
	 * @return token
	 */
	@Transactional
	@Override
	public String loginByEmailOrMobile(LoginDTO login) {
		
		
		 Seller seller = sellerRepository.getUser(login.getMailOrMobile());
		 System.out.println("password--->"+seller.getPassword());
//		 String password=encoder.encode(login.getPassword());
//		 System.out.println("password--->"+password);
		if (seller != null) {
			if ( (encoder.matches(login.getPassword(), seller.getPassword()))) {
				String token=jwt.generateToken(seller.getSellerId(), Token.WITH_EXPIRE_TIME);
				System.out.println(token);
				return token;
			}
		}
		return null;
	}
	/**
	 * Api for forget password
	 * @param emailAddress
	 * @Return 
	 */
	@Transactional
	@Override
	public String forgotpassword(@Valid String emailAddress) {
		Mail mail = new Mail();
		Optional<Seller> optionalSeller = sellerRepository.findByEmailAddress(emailAddress);
		return optionalSeller.filter(seller -> {	
			return seller != null;
		}).map(seller -> {
			mail.setTo(seller.getEmailAddress());
			mail.setSubject(Constants.RESET_PASSWORD_LINK);
			mail.setContext("Hi " + seller.getSellerName() + " " + Constants.RESET_PASSWORD_LINK
					+ Constants.RESET_PASSWORD_LINK + jwt.generateToken(seller.getSellerId(), Token.WITH_EXPIRE_TIME));
			producer.sendToQueue(mail);
			consumer.receiveMail(mail);
			return env.getProperty("403");
		}).orElseThrow(() -> new SellerException(env.getProperty("104")));
		
	}

	/**
	 * Api for reset password
	 * @param token
	 * @RequestBody forgetPasswordDto
	 * @Return 
	 */
	@Transactional
	@Override
	public String resetpassword(@Valid String token, sellerForgetPasswordDto forgetPasswordDto) {
		
		Long id = jwt.decodeToken(token);
		Optional<Seller> optionalSeller = sellerRepository.findById(id);
		return optionalSeller.filter(seller -> {	
			return seller != null;
		}).map(seller -> {
		String newPassword=encoder.encode(forgetPasswordDto.getPassword());
		seller.setPassword(newPassword);
		seller.setUpdatedTime(LocalDateTime.now());
		 sellerRepository.save(seller);
			return env.getProperty("203");
		}).orElseThrow(() -> new SellerException(env.getProperty("104")));
		
	}	 
}
