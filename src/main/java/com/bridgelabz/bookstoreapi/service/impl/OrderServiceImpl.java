package com.bridgelabz.bookstoreapi.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
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
		int countOfBooks=0;
		User userdetails = userRepository.findById(id)
				.orElseThrow(() -> new UserException(400, env.getProperty("104")));

		List<OrderDetails> books = userdetails.getOrderBookDetails();
		 for(OrderDetails cart:books) {
        	 if(!cart.getBooksList().isEmpty()) {
        		 
				countOfBooks++;
        	 }
         }
        return countOfBooks;
	}

	@Override
	public List<OrderDetails> getOrderConfrim(String token) {
		Long id = jwt.decodeToken(token);
		User userdetails = userRepository.findById(id)
				.orElseThrow(() -> new UserException(400, env.getProperty("104")));

		OrderDetails orderDetails = new OrderDetails();
		Random random = new Random();
		ArrayList<Book> list = new ArrayList<>();
		
		/**
		 * adding the books from cartlist to orderlist by generating the OrderId
		 */
		userdetails.getCartBooks().forEach((cart) -> {
			
			cart.getBooksList().forEach(book -> {
				long orderId;
				/**
				 * If order is confrim decreasing the numberOfBooks in BookList
				 */
				for(Quantity qt:cart.getQuantityOfBooks()) {
					
					Long noOfBooks = book.getNoOfBooks() - qt.getQuantityOfBook();
					book.setNoOfBooks(noOfBooks);
					Book bb = bookRepository.save(book);
				
				try {
					list.add(bb);
					orderId = random.nextInt(1000000);
					if (orderId < 0) {
						orderId = orderId * -1;
					}
					orderDetails.setOrderId(orderId);
					orderDetails.setTotalPrice(qt.getTotalprice());
					orderDetails.setOrderPlaceTime(LocalDateTime.now());
					orderDetails.setBooksList(list);
		           
				} catch (Exception e) {
					throw new UserException(401, env.getProperty("701"));
				}

				}
			});

		});
		userdetails.getOrderBookDetails().add(orderDetails);
		/**
		 * clearing the cart after added to the orderlist
		 */
		userdetails.getCartBooks().clear();
		
		try {
			userRepository.save(userdetails);
		} catch (Exception e) {
			throw new UserException(401, env.getProperty("701"));
		}
		return userdetails.getOrderBookDetails();

	}
	
	/**
	 * If order is confrim decreasing the numberOfBooks in BookList
	 */
//	public void descBook(ArrayList<Book> list) {
//		for(Book book:list) {
//			Long noOfBooks = book.getNoOfBooks() - quantity;
//			book.setNoOfBooks(noOfBooks);
//			bookRepository.save(book);
//		}
//
//		
//	}
	}

