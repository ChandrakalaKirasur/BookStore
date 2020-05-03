package com.bridgelabz.bookstoreapi.service.impl;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.bridgelabz.bookstoreapi.configuration.Consumer;
import com.bridgelabz.bookstoreapi.configuration.Producer;
import com.bridgelabz.bookstoreapi.constants.Constants;
import com.bridgelabz.bookstoreapi.dto.LoginDTO;
import com.bridgelabz.bookstoreapi.dto.Mail;
import com.bridgelabz.bookstoreapi.dto.RegisterDto;
import com.bridgelabz.bookstoreapi.entity.Seller;
import com.bridgelabz.bookstoreapi.exception.SellerException;
import com.bridgelabz.bookstoreapi.repository.SellerRepository;
import com.bridgelabz.bookstoreapi.response.Response;
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
	
	/**
	 * Saves the user details
	 */
	@Override
	public void sellerRegistration(RegisterDto register) {

		if (sellerRepository.findByEmailAddress(register.getEmailAddress()).isPresent())
			//throw new SellerException(HttpStatus.FOUND.value(), env.getProperty("103"));

		register.setPassword(encoder.encode(register.getPassword()));
		Seller seller = new Seller(register);
		Mail mail = new Mail();
		try {
			Seller usr = sellerRepository.save(seller);
			if (usr != null) {
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
	}


//	/**
//	 * Update the user verification status if the received token is valid
//	 * @return integer value that is number of record that had been updated
//	 */
//	@Override
//	public int updateVerificationStatus(String token) {
//		Long id = jwt.decodeToken(token);
//		try {
//			return repository.updateUserVerificationStatus(id, LocalDateTime.now());
//		} catch (UserException e) {
//			throw new UserException(400, env.getProperty("102"));
//		}
//	}

	/**
	 * Login to the application using login credential
	 * @return user details which is necessary
	 */
	@Override
	public Response loginByEmailOrMobile(LoginDTO login) {
		
		Seller seller = null;
		boolean email = Pattern.compile("^((\"[\\w-\\s]+\")|([\\w-]+(?:\\.[\\w-]+)*)|(\"[\\w-\\s]+\")([\\w-]+(?:\\.[\\w-]+)*))(@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)$)|(@\\[?((25[0-5]\\.|2[0-4][0-9]\\.|1[0-9]{2}\\.|[0-9]{1,2}\\.))((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\\.){2}(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\\]?$)").matcher(login.getMailOrMobile()).matches();
		boolean mobile = Pattern.compile("^[0-9]{10}$").matcher(login.getMailOrMobile()).matches();
		Long mbl = mobile ? Long.parseLong(login.getMailOrMobile()) : 0; 
		seller = email ? sellerRepository.findByEmailAddress(login.getMailOrMobile()).orElseThrow(() -> new SellerException(404, env.getProperty("104"))) :
			   mobile ? sellerRepository.findByMobile(mbl).orElseThrow(() -> new SellerException(404, env.getProperty("104"))) : null;

		if (seller.isVerificationStatus() && seller !=null) {
			if (encoder.matches(login.getPassword(), seller.getPassword())) {
				return new Response(HttpStatus.OK.value(), env.getProperty("202"), 
						jwt.generateToken(seller.getSellerId(),Token.WITHOUT_EXPIRE_TIME));
			}
			throw new SellerException(401, env.getProperty("401"));
		}
		return null;
	}

//	/**
//	 * Send the jwt token to the user mail
//	 */
//	@Override
//	public void sendTokentoMail(String emailAddress) {
//
//		User user = repository.findByEmailAddress(emailAddress)
//				.orElseThrow(() -> new UserException(404, env.getProperty("104")));
//		Mail mail = new Mail();
//		mail.setTo(emailAddress);
//		mail.setSubject(Constants.RESET_MSG);
//		mail.setContext(Constants.RESET_PASSWORD_LINK + jwt.generateToken(user.getUserId()));
//		producer.sendToQueue(mail);
//		consumer.receiveMail(mail);
//	}

//	/**
//	 * Provides the link to reset the password to user mail address
//	 */
//	@Override
//	public int resetPassword(String token, String newPassword) {
//		Long id = jwt.decodeToken(token);
//		return repository.updatePassword(id, encoder.encode(newPassword), LocalDateTime.now());
//	}
}
