package com.bridgelabz.bookstoreapi.configuration;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bridgelabz.bookstoreapi.constants.Constants;
import com.bridgelabz.bookstoreapi.dto.Mail;

@Component
public class Producer {

	@Autowired
	private AmqpTemplate amqpTemplate;
	
	public void sendToQueue(Mail mail) {
		amqpTemplate.convertAndSend(Constants.EXCHANGE_NAME, Constants.ROUTING_KEY, mail);
	}
	
}