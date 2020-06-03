package com.bridgelabz.bookstoreapi.utility;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import com.bridgelabz.bookstoreapi.dto.Mail;
import com.bridgelabz.bookstoreapi.entity.OrderDetails;
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
		Context context = new Context();
		context.setVariable("username", userdetails.getName()+",");
		context.setVariable("orderdetails", orderDetails);
		String body = templateEngine.process("orderdetails", context);
		sendM(userdetails.getEmail(),"Order Comfirmation Mail",body,true);
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