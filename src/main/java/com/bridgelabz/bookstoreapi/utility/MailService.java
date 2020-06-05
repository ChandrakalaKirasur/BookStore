package com.bridgelabz.bookstoreapi.utility;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import com.bridgelabz.bookstoreapi.constants.Constants;
import com.bridgelabz.bookstoreapi.dto.Mail;
import com.bridgelabz.bookstoreapi.entity.OrderDetails;
import com.bridgelabz.bookstoreapi.entity.Quantity;
import com.bridgelabz.bookstoreapi.entity.User;


@Component
public class MailService {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
    private SpringTemplateEngine templateEngine;

	public void sendMail(Mail mail) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(mail.getTo());
		message.setSubject(mail.getSubject());
		message.setText(mail.getContext());
		mailSender.send(message);
	}
	
	public void send(Mail mail) {
		Context context = new Context();
		context.setVariable("title", mail.getSubject());
		context.setVariable("description", mail.getContext());
		String body = templateEngine.process("mailtemplate", context);
		sendM(mail.getTo(),mail.getSubject(),body,true);
	}
	
	public void orderSuccessMail(User userdetails, OrderDetails orderDetails) {
		Double totalprice= 0.0;
		for(Quantity quantity: orderDetails.getQuantityOfBooks()) {
			totalprice = totalprice+quantity.getTotalprice();
		}
		Context context = new Context();
		context.setVariable("username", userdetails.getName()+",");
		context.setVariable("orderdetails", orderDetails);
		context.setVariable("totalPrice", totalprice);
		String body = templateEngine.process("orderdetails", context);
		sendM(userdetails.getEmail(),"Order Comfirmation Mail",body,true);
	}
	
	public void wishlistNotificationMail(User userdetails,Long bookId) {
		
		Context context = new Context();
		context.setVariable("username", userdetails.getName()+","+ Constants.BOOK_STORE__LINK + bookId);
		context.setVariable("userdetails", userdetails);
		
		String body = templateEngine.process("notification", context);
		sendM(userdetails.getEmail(),"Book Notification Mail",body,true);
	}
	
	private void sendM(String to, String subject, String text, Boolean isHtml) {
		try {
            MimeMessage mail = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, isHtml);
            mailSender.send(mail);
		}
		catch (Exception e) {
			e.printStackTrace();
        }
    }

}