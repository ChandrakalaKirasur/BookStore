package com.bridgelabz.bookstoreapi.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstoreapi.configuration.Consumer;
import com.bridgelabz.bookstoreapi.configuration.Producer;
import com.bridgelabz.bookstoreapi.constants.Constants;
import com.bridgelabz.bookstoreapi.dto.LoginDTO;
import com.bridgelabz.bookstoreapi.dto.Mail;
import com.bridgelabz.bookstoreapi.dto.RegisterDto;
import com.bridgelabz.bookstoreapi.dto.sellerForgetPasswordDto;
import com.bridgelabz.bookstoreapi.entity.OrderDetails;
import com.bridgelabz.bookstoreapi.entity.Seller;
import com.bridgelabz.bookstoreapi.entity.User;
import com.bridgelabz.bookstoreapi.exception.SellerException;
import com.bridgelabz.bookstoreapi.exception.UserException;
import com.bridgelabz.bookstoreapi.exception.UserException;
import com.bridgelabz.bookstoreapi.repository.UserRepository;
import com.bridgelabz.bookstoreapi.response.UserResponse;
import com.bridgelabz.bookstoreapi.service.UserService;
import com.bridgelabz.bookstoreapi.utility.JWTUtil;
import com.bridgelabz.bookstoreapi.utility.Token;

@Service
@PropertySource("classpath:message.properties")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

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
	 * 
	 * @return
	 */
	@Transactional
	@Override
	public User userRegistration(RegisterDto register) {

		Optional<User> useremail = userRepository.findUserByEmail(register.getEmailAddress());

		if (useremail.isPresent())
			throw new UserException(208, env.getProperty("103"));

		register.setPassword(encoder.encode(register.getPassword()));
		User user = new User(register);
		Mail mail = new Mail();
		user.setVerified(false);
		User usr = userRepository.save(user);
		try {
			if (usr != null) {
				mail.setTo(user.getEmail());
				mail.setSubject(Constants.REGISTRATION_STATUS);
				mail.setContext("Hi " + user.getName() + " " + Constants.REGISTRATION_MESSAGE
						+ Constants.VERIFICATION_LINK + jwt.generateToken(user.getUserId(), Token.WITH_EXPIRE_TIME));
				producer.sendToQueue(mail);
				consumer.receiveMail(mail);
			}
		} catch (UserException e) {
			throw new UserException(400, env.getProperty("102"));
		}
		return usr;
	}

	@Transactional
	@Override
	public String loginByEmailOrMobile(LoginDTO login) {

		User user = null;
		boolean email = Pattern.compile(
				"^((\"[\\w-\\s]+\")|([\\w-]+(?:\\.[\\w-]+)*)|(\"[\\w-\\s]+\")([\\w-]+(?:\\.[\\w-]+)*))(@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)$)|(@\\[?((25[0-5]\\.|2[0-4][0-9]\\.|1[0-9]{2}\\.|[0-9]{1,2}\\.))((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\\.){2}(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\\]?$)")
				.matcher(login.getMailOrMobile()).matches();
		boolean mobile = Pattern.compile("^[0-9]{10}$").matcher(login.getMailOrMobile()).matches();
		Long mbl = mobile ? Long.parseLong(login.getMailOrMobile()) : 0;
		user = email
				? userRepository.findUserByEmail(login.getMailOrMobile())
						.orElseThrow(() -> new UserException(404, env.getProperty("104")))
				: mobile ? userRepository.findByMobile(mbl)
						.orElseThrow(() -> new UserException(404, env.getProperty("104"))) : null;
		if (user.isVerified() && user != null) {
			if (encoder.matches(login.getPassword(), user.getPassword())) {

				String token = jwt.generateToken(user.getUserId(), Token.WITHOUT_EXPIRE_TIME);
				return token;
			}
			throw new UserException(208, env.getProperty("404"));
		}
		return null;
	}

	/**
	 * Update the user verification status if the received token is valid
	 * 
	 * @return integer value that is number of record that had been updated
	 */
	@Override
	public boolean updateVerificationStatus(String token) {

		Long id = jwt.decodeToken(token);

		User user = userRepository.findUserById(id).orElseThrow(() -> new UserException(400, env.getProperty("104")));

		user.setVerified(true);
		boolean users = userRepository.save(user) != null ? true : false;

		return users;
	}

	@Transactional
	@Override
	public String forgotpassword(@Valid String emailAddress) {
		Mail mail = new Mail();
		Optional<User> optionalUser = userRepository.findUserByEmail(emailAddress);
		return optionalUser.filter(user -> {
			return user != null;
		}).map(user -> {
			mail.setTo(user.getEmail());
			mail.setSubject(Constants.RESET_PASSWORD_LINK);
			mail.setContext("Hi " + user.getName() + " " + Constants.USER_RESET_PASSWORD_LINK
					+ Constants.RESET_PASSWORD_LINK + jwt.generateToken(user.getUserId(), Token.WITH_EXPIRE_TIME));
			producer.sendToQueue(mail);
			consumer.receiveMail(mail);
			return env.getProperty("403");
		}).orElseThrow(() -> new SellerException(env.getProperty("104")));

	}

	/**
	 * Api for reset password
	 * 
	 * @param token
	 * @RequestBody forgetPasswordDto
	 * @Return
	 */
	@Transactional
	@Override
	public String resetpassword(@Valid String token, sellerForgetPasswordDto forgetPasswordDto) {

		Long id = jwt.decodeToken(token);
		Optional<User> optionalUser = userRepository.findById(id);
		return optionalUser.filter(user -> {
			return user != null;
		}).map(user -> {
			String newPassword = encoder.encode(forgetPasswordDto.getPassword());
			user.setPassword(newPassword);
			userRepository.save(user);
			return env.getProperty("203");
		}).orElseThrow(() -> new SellerException(env.getProperty("104")));

	}

	@Transactional
	@Override
	public User getUser(String token) {

		Long id = jwt.decodeToken(token);
		User userdetails = userRepository.findById(id)
				.orElseThrow(() -> new UserException(400, env.getProperty("104")));
		return userdetails;
	}

}
