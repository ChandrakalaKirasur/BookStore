package com.bridgelabz.bookstoreapi.service.impl;

import java.io.IOException;
import java.util.Map;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstoreapi.configuration.Consumer;
import com.bridgelabz.bookstoreapi.configuration.Producer;
import com.bridgelabz.bookstoreapi.constants.Constants;
import com.bridgelabz.bookstoreapi.dto.AdminDto;
import com.bridgelabz.bookstoreapi.dto.AdminPasswordResetDto;
import com.bridgelabz.bookstoreapi.dto.LoginDTO;
import com.bridgelabz.bookstoreapi.dto.Mail;
import com.bridgelabz.bookstoreapi.entity.Admin;
import com.bridgelabz.bookstoreapi.entity.Book;
import com.bridgelabz.bookstoreapi.exception.AdminException;
import com.bridgelabz.bookstoreapi.exception.BookException;
import com.bridgelabz.bookstoreapi.repository.AdminRepository;
import com.bridgelabz.bookstoreapi.repository.BookRepository;
import com.bridgelabz.bookstoreapi.service.AdminService;
import com.bridgelabz.bookstoreapi.utility.JWTUtil;
import com.bridgelabz.bookstoreapi.utility.MailService;
import com.bridgelabz.bookstoreapi.utility.Token;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@PropertySource("classpath:message.properties")
public class AdminServiceImpl implements AdminService{

	@Autowired
	AdminRepository adminRepo;
	@Autowired
	private BookRepository bookRepo;
	@Autowired
	private JWTUtil util;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private Producer producer;
	@Autowired
	private Consumer consumer;
	@Autowired
	private Environment env;
	@Autowired
	private MailService mailService;
	@Autowired
	private RestHighLevelClient client;

	@Autowired
	private ObjectMapper objectMapper;
	
	@Override
	public boolean register(AdminDto adminDto) {
		if (adminRepo.existsByName(adminDto.getName())) {
			return false;
		}
		Admin newAdmin = new Admin();
		BeanUtils.copyProperties(adminDto, newAdmin);
		newAdmin.setPassword(encoder.encode(newAdmin.getPassword()));
		adminRepo.save(newAdmin);
		sendNotification(newAdmin);
		return true;
	}
	private boolean sendNotification(Admin admin) {
		Mail mail = new Mail();
		try {     
				mail.setTo(admin.getEmail());
				mail.setSubject(Constants.REGISTRATION_STATUS);
				mail.setContext("Hi " + admin.getName() + " " + Constants.REGISTRATION_MESSAGE
						+ Constants.ADMIN_VERIFICATION_LINK + util.generateToken(admin.getAdminId(), Token.WITH_EXPIRE_TIME));
				//producer.sendToQueue(mail);
				//consumer.receiveMail(mail);
				mailService.sendMail(mail);
		} catch (AdminException e) {
			throw new AdminException(400, env.getProperty("102"));
		}
		return true;
	}
	
	@Override
	public boolean verifyEmail(String token) {
		System.out.println(util.decodeToken(token));
		Admin fetchedAdmin = adminRepo.findByAdminId(util.decodeToken(token)).orElseThrow(() -> new AdminException(400, "Admin not found"));
		if (!fetchedAdmin.isVerified()) {
			fetchedAdmin.setVerified(true);
			adminRepo.save(fetchedAdmin);
			return true;
		}
		return false;	
	}
	
	@Override
	public Admin login(LoginDTO adminLoginDto) {
		Admin fetchedAdmin = adminRepo.findByEmail(adminLoginDto.getMailOrMobile()).orElseThrow(() -> new AdminException(400, "Admin not found"));
		if(encoder.matches(adminLoginDto.getPassword(), fetchedAdmin.getPassword())) {
			return fetchedAdmin;
		}
		return null;
	}

	@Override
	public boolean sendLinkForPassword(String email) {
		Admin fetchedAdmin = adminRepo.findByEmail(email).orElseThrow(() -> new AdminException(400, "Admin not found"));
		Mail mail = new Mail();
		try {     
				mail.setTo(fetchedAdmin.getEmail());
				mail.setSubject("Reset password");
				mail.setContext("Hi " + fetchedAdmin.getName() + " " + Constants.RESET_MSG
						+ Constants.ADMIN_RESET_PASSWORD_LINK + util.generateToken(fetchedAdmin.getAdminId(), Token.WITH_EXPIRE_TIME));
				//producer.sendToQueue(mail);
				//consumer.receiveMail(mail);
				mailService.sendMail(mail);
		} catch (AdminException e) {
			throw new AdminException(400, env.getProperty("102"));
		}
		return true;
	}

	@Override
	public boolean resetAdminPassword(AdminPasswordResetDto resetDto) {
		Admin fetchedAdmin = adminRepo.findByAdminId(util.decodeToken(resetDto.getToken())).orElseThrow(() -> new AdminException(400, "Admin not found"));
		fetchedAdmin.setPassword(encoder.encode(resetDto.getPassword()));
		adminRepo.save(fetchedAdmin);
		return true;
	}
	@Override
	public boolean verifyBook(String booktoken,String token) {
		adminRepo.findByAdminId(util.decodeToken(token)).orElseThrow(() -> new AdminException(400, "Admin not found"));
		Book fetchedBookForVerification=bookRepo.findById(util.decodeToken(booktoken)).orElseThrow(()-> new BookException(400,"book doesn't exist"));
		fetchedBookForVerification.setBookVerified(true);
		Book bookSave = bookRepo.save(fetchedBookForVerification);
		
		Map<String, Object> documentMapper = objectMapper.convertValue(bookSave, Map.class);
		IndexRequest indexRequest = new IndexRequest(Constants.INDEX, Constants.TYPE, String.valueOf(bookSave.getBookId()))
				.source(documentMapper);
		try {
			client.index(indexRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bookSave != null;
	}

}
