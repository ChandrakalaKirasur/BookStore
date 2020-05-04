package com.bridgelabz.bookstoreapi.configuration;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bridgelabz.bookstoreapi.constants.Constants;
import com.bridgelabz.bookstoreapi.dto.Mail;
import com.bridgelabz.bookstoreapi.utility.MailService;

@Component
public class Consumer {

	@Autowired
	private MailService mailSender;
	
	//@RabbitListener(queues = Constants.QUEUE_NAME)
	public void receiveMail(Mail mail) {
		mailSender.sendMail(mail);
	}
	
}
