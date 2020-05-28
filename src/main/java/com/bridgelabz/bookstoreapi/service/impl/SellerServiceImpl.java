package com.bridgelabz.bookstoreapi.service.impl;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.bridgelabz.bookstoreapi.configuration.Consumer;
import com.bridgelabz.bookstoreapi.configuration.Producer;
import com.bridgelabz.bookstoreapi.constants.Constants;
import com.bridgelabz.bookstoreapi.dto.LoginDTO;
import com.bridgelabz.bookstoreapi.dto.Mail;
import com.bridgelabz.bookstoreapi.dto.RegisterDto;
import com.bridgelabz.bookstoreapi.dto.sellerForgetPasswordDto;
import com.bridgelabz.bookstoreapi.entity.Book;
import com.bridgelabz.bookstoreapi.entity.Seller;
import com.bridgelabz.bookstoreapi.entity.User;
import com.bridgelabz.bookstoreapi.exception.SellerException;
import com.bridgelabz.bookstoreapi.exception.UserException;
import com.bridgelabz.bookstoreapi.repository.BookRepository;
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
	private PasswordEncoder encoder;

	@Autowired
	private Producer producer;
	
	@Autowired
	private Consumer consumer;
	
	@Autowired
	private JWTUtil jwt;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private BookRepository bookRepository;
	
	Seller seller =new Seller();
	
	/**
	 * Saves the user details
	 * @param register
	 * @Return 
	 */
	@Transactional
	@Override
	public Seller sellerRegistration(RegisterDto register) {

		if (sellerRepository.findByEmail(register.getEmail()).isPresent())
			//throw new SellerException(HttpStatus.FOUND.value(), env.getProperty("103"));

		register.setPassword(encoder.encode(register.getPassword()));
		System.out.println("password--->"+register.getPassword());
		Seller seller = new Seller(register);
		Mail mail = new Mail();
		try {
			String password=encoder.encode(register.getPassword());
			seller.setPassword(password);
			Seller sellr = sellerRepository.save(seller);
			if (sellr != null) {
				mail.setTo(seller.getEmail());
				mail.setSubject(Constants.REGISTRATION_STATUS);
//				mail.setContext("Hi " + seller.getSellerName() + " " + Constants.REGISTRATION_MESSAGE
//						+ Constants.SELLER_VERIFICATION_LINK + jwt.generateToken(seller.getSellerId(), Token.WITH_EXPIRE_TIME));
				mail.setContext("Hi " + seller.getSellerName() + " " + Constants.VERIFY__LINK
						+ Constants.VERIFY__LINK + jwt.generateToken(seller.getSellerId(), Token.WITH_EXPIRE_TIME));
				producer.sendToQueue(mail);
				consumer.receiveMail(mail);
			}
		} catch (SellerException e) {
			throw new SellerException(400, env.getProperty("102"));
		}
		return seller;
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
			if ( seller.isVerificationStatus()&&(encoder.matches(login.getPassword(), seller.getPassword()))) {
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
	public String forgotpassword(@Valid String email) {
		Mail mail = new Mail();
		Optional<Seller> optionalSeller = sellerRepository.findByEmail(email);
		return optionalSeller.filter(seller -> {	
			return seller != null;
		}).map(seller -> {
			mail.setTo(seller.getEmail());
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
	@Transactional
	@Override
	public List<Seller> getSellers() {
		List<Seller> users = sellerRepository.getSellers();
		return users;
	}


	@Transactional
	@Override
	public Seller getSingleUser(Long id) {
		try {
			

			Seller seller = sellerRepository.findSellerById(id);

			return seller;
		} catch (SellerException e) {
			throw  new SellerException(env.getProperty("104"));
		}
	}


	@Transactional
	@Override
	public boolean updateVerificationStatus(String token) {	
			Long id = jwt.decodeToken(token);
			Seller seller = sellerRepository.findSellerById(id);
			seller.setVerificationStatus(true);
			boolean sellers = sellerRepository.save(seller) != null ? true : false;
	         return sellers;
		}
	
	@Transactional
	@Override
	public List<Book> getSellerBooks(String token, Integer page){
		Long sId = jwt.decodeToken(token);
		Integer start = (page-1)*12;
		return bookRepository.findSellerBook(start, sId);
	}
	}

