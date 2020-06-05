package com.bridgelabz.bookstoreapi.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstoreapi.configuration.Consumer;
import com.bridgelabz.bookstoreapi.configuration.Producer;
import com.bridgelabz.bookstoreapi.constants.Constants;
import com.bridgelabz.bookstoreapi.dto.Mail;
import com.bridgelabz.bookstoreapi.entity.Book;
import com.bridgelabz.bookstoreapi.entity.CartDetails;
import com.bridgelabz.bookstoreapi.entity.OrderDetails;
import com.bridgelabz.bookstoreapi.entity.Quantity;
import com.bridgelabz.bookstoreapi.entity.User;
import com.bridgelabz.bookstoreapi.exception.UserException;
import com.bridgelabz.bookstoreapi.repository.BookRepository;
import com.bridgelabz.bookstoreapi.repository.UserRepository;
import com.bridgelabz.bookstoreapi.service.OrderService;
import com.bridgelabz.bookstoreapi.utility.JWTUtil;
import com.bridgelabz.bookstoreapi.utility.MailService;
import com.bridgelabz.bookstoreapi.utility.Token;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private JWTUtil jwt;

	@Autowired
	private Environment env;

	@Autowired
	private Producer producer;

	@Autowired
	private Consumer consumer;

	@Autowired
	private MailService mailSender;
	
	@Transactional
	@Override
	public List<OrderDetails> getOrderList(String token) {
		Long id = jwt.decodeToken(token);
		User userdetails = userRepository.findById(id)
				.orElseThrow(() -> new UserException(400, env.getProperty("104")));

		return userdetails.getOrderBookDetails();

	}

	@Transactional
	@Override
	public int getCountOfBooks(String token) {
		Long id = jwt.decodeToken(token);
		int countOfBooks = 0;
		User userdetails = userRepository.findById(id)
				.orElseThrow(() -> new UserException(400, env.getProperty("104")));

		List<OrderDetails> books = userdetails.getOrderBookDetails();
		for (OrderDetails cart : books) {
			if (!cart.getBooksList().isEmpty()) {

				countOfBooks++;
			}
		}
		return countOfBooks;
	}

	@Transactional
	@Override
	public OrderDetails getOrderConfrim(String token) {
		Long id = jwt.decodeToken(token);
		User userdetails = userRepository.findById(id)
				.orElseThrow(() -> new UserException(400, env.getProperty("104")));

		OrderDetails orderDetails = new OrderDetails();
		Random random = new Random();
		ArrayList<Book> list = new ArrayList<>();
		ArrayList<Quantity> quantitydetails = new ArrayList<>();
		ArrayList<String> details = new ArrayList<>();
		/**
		 * adding the books from cartlist to orderlist by generating the OrderId
		 */
		userdetails.getCartBooks().forEach((cart) -> {

			cart.getBooksList().forEach(book -> {
				long orderId;
				/**
				 * If order is confrim decreasing the numberOfBooks in BookList
				 */
				for (Quantity qt : cart.getQuantityOfBooks()) {

					Long noOfBooks = book.getNoOfBooks() - qt.getQuantityOfBook();
					book.setNoOfBooks(noOfBooks);
					Book bb = bookRepository.save(book);

					try {
						list.add(bb);
						orderId = random.nextInt(1000000);
						if (orderId < 0) {
							orderId = orderId * -1;
						}
						quantitydetails.add(qt);
						orderDetails.setOrderId(orderId);
						orderDetails.setQuantityOfBooks(quantitydetails);
						orderDetails.setOrderPlaceTime(LocalDateTime.now());
						orderDetails.setBooksList(list);
                        details.add("orderId:"+orderId+"\n"+"BookName:"+book.getBookName()+"\n"+"Quantity:"+qt.getQuantityOfBook()+"\n"+"TotalPrice:"+qt.getTotalprice());
                        
					} catch (Exception e) {
						throw new UserException(401, env.getProperty("701"));
					}

				}
			});

		});
		
		userdetails.getOrderBookDetails().add(orderDetails);
		
		String data = "";
		for(String dt:details) {
			data+=dt+"\n"+"=========>"+"\n";		
		}
		/**
		 * sending the mailto the user
		 */
//		this.sendMail(userdetails, data);
		mailSender.orderSuccessMail(userdetails,orderDetails);
		/**
		 * clearing the cart after added to the orderlist
		 */
		userdetails.getCartBooks().clear();

		try {
			userRepository.save(userdetails);
		} catch (Exception e) {
			throw new UserException(401, env.getProperty("701"));
		}
		return orderDetails;

	}

	
	public void sendMail(User userdetails,String data) {
		Mail mail = new Mail();
		mail.setTo(userdetails.getEmail());
		mail.setSubject(Constants.REGISTRATION_STATUS);
		mail.setContext("Hi " + userdetails.getName() + " " + "UR Order Is Confired Invoice is attached below"+"\n" + data);
		producer.sendToQueue(mail);
		consumer.receiveMail(mail);
	}

	 @Transactional
	 @Override
	public boolean getbookConfrim(String token, Long bookId) {
		Long id = jwt.decodeToken(token);
		User userdetails = userRepository.findById(id)
				.orElseThrow(() -> new UserException(400, env.getProperty("104")));

		for (OrderDetails order : userdetails.getOrderBookDetails()) {
			boolean notExist = order.getBooksList().stream().noneMatch(books -> books.getBookId().equals(bookId));

			if (!notExist) {
				return true;
			}
		}

		return false;

	}
}
