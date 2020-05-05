package com.bridgelabz.bookstoreapi.configuration;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bridgelabz.bookstoreapi.constants.Constants;

@Configuration
public class BookstoreConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public Exchange mailExchange() {
		return new DirectExchange(Constants.EXCHANGE_NAME);
	}
	
	@Bean
	public Queue mailQueue() {
		return new Queue(Constants.QUEUE_NAME);
	}
	
	@Bean
	public Binding declareBinding(Queue mailQueue, DirectExchange mailExchange) {
		return BindingBuilder.bind(mailQueue).to(mailExchange).with(Constants.ROUTING_KEY);
	}
	
	@Bean
	public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
	
	@Bean(destroyMethod = "close")
	public RestHighLevelClient elasticsearchClient() {

		RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost",9200,"http")));

		return client;
	}
}
