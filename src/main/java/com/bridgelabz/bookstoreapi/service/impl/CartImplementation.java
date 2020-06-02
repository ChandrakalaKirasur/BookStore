/**
 * @author Saikiran(Msk)
 */
package com.bridgelabz.bookstoreapi.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstoreapi.dto.CartdetailsDto;
import com.bridgelabz.bookstoreapi.entity.Book;
import com.bridgelabz.bookstoreapi.entity.CartDetails;
import com.bridgelabz.bookstoreapi.entity.Quantity;
import com.bridgelabz.bookstoreapi.entity.User;
import com.bridgelabz.bookstoreapi.exception.UserException;
import com.bridgelabz.bookstoreapi.repository.BookRepository;
import com.bridgelabz.bookstoreapi.repository.CartRepository;
import com.bridgelabz.bookstoreapi.repository.QuantityRepository;
import com.bridgelabz.bookstoreapi.repository.UserRepository;
import com.bridgelabz.bookstoreapi.service.CartService;
import com.bridgelabz.bookstoreapi.utility.JWTUtil;

@Service
@PropertySource("classpath:message.properties")
public class CartImplementation implements CartService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JWTUtil jwt;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private QuantityRepository quantityRepository;

	@Autowired
	private Environment env;

	@Transactional
	@Override
	public List<CartDetails> getBooksfromCart(String token) {
		Long id = jwt.decodeToken(token);

		User user = userRepository.findUserById(id).orElseThrow(() -> new UserException(401, env.getProperty("104")));
		List<CartDetails> cartBooks = user.getCartBooks();

		return cartBooks;
	}
	
	@Transactional
	@Override
	public int getCountOfBooks(String token) {
		Long id = jwt.decodeToken(token);
         int countOfBooks=0;
		User user = userRepository.findUserById(id).orElseThrow(() -> new UserException(401, env.getProperty("104")));
		List<CartDetails> cartBooks = user.getCartBooks();
         for(CartDetails cart:cartBooks) {
        	 if(!cart.getBooksList().isEmpty()) {
        		 countOfBooks++;
        	 }
         }
		return countOfBooks;
	}

	@Transactional
	@Override
	public List<CartDetails> addBooksToCart(String token, long bookId) {
		Long id = jwt.decodeToken(token);
		
		User user = userRepository.findUserById(id).orElseThrow(() -> new UserException(401, env.getProperty("104")));

		Book book = bookRepository.findById(bookId).orElseThrow(() -> new UserException(201, env.getProperty("4041")));
		/**
		 * Getting the bookList
		 */
		List<Book> books = null;
		for (CartDetails d : user.getCartBooks()) {
			books = d.getBooksList();
		}
		/**
		 * For the first time adding the book the cartList
		 */
		
		if (books == null) {
			User userdetails = this.cartbooks(book,user);
			return userRepository.save(userdetails).getCartBooks();
		}
		/**
		 * Checking whether book is already present r not
	     */
		
		Optional<Book> cartbook = books.stream().filter(t -> t.getBookId() == bookId).findFirst();

		if (cartbook.isPresent()) {
			throw new UserException(401, env.getProperty("505"));
		} else {
			User userdetails = this.cartbooks(book,user);
			return userRepository.save(userdetails).getCartBooks();
		}

	}

	
	public User cartbooks(Book book,User user) {
		long quantity=1;
        CartDetails cart = new CartDetails();
		Quantity qunatityofbook = new Quantity();
		
		ArrayList<Book> booklist = new ArrayList<>();
		ArrayList<Quantity> quantitydetails = new ArrayList<Quantity>();
		/**
		 * adding the book details
		 */
		booklist.add(book);
		cart.setPlaceTime(LocalDateTime.now());
		cart.setBooksList(booklist);
		/**
		 * adding the quantity to the book
		 */
		qunatityofbook.setQuantityOfBook(quantity);
		qunatityofbook.setTotalprice(book.getBookPrice());
		quantitydetails.add(qunatityofbook);
		
		cart.setQuantityOfBooks(quantitydetails);
		/**
		 * saving the complete cart in user
		 */
		user.getCartBooks().add(cart);
		return user;
	}
	
	@Transactional
	@Override
	public CartDetails addBooksQuantityInCart(String token, Long bookId, CartdetailsDto bookQuantityDetails) {

		Long id = jwt.decodeToken(token);
		Long quantityId = bookQuantityDetails.getQuantityId();
		Long quantity = bookQuantityDetails.getQuantityOfBook();
        
		User user = userRepository.findUserById(id).orElseThrow(() -> new UserException(401, env.getProperty("104")));
		 Book book = bookRepository.findById(bookId).orElseThrow(() -> new UserException(401, env.getProperty("104")));
		 double totalprice=book.getBookPrice()*(quantity+1);
		boolean notExist = false;
		for (CartDetails cartt : user.getCartBooks()) {
			
			if(!cartt.getBooksList().isEmpty()) {
				
				/**
				 * checking the weither number of books available
				 */
			notExist = cartt.getBooksList().stream()
					.noneMatch(books -> books.getBookId().equals(bookId));
			
			if (!notExist) {

				Quantity quantityDetails = quantityRepository.findById(quantityId).orElseThrow(() -> new UserException(401, env.getProperty("104")));
				quantityDetails.setQuantityOfBook(quantity+1);
				quantityDetails.setTotalprice(totalprice);
				quantityRepository.save(quantityDetails);
				return cartt;
		
				}

			}
		}

		return null;

	}
	
	@Transactional
	@Override
	public CartDetails descBooksQuantityInCart(String token, Long bookId, CartdetailsDto bookQuantityDetails) {

		Long id = jwt.decodeToken(token);
		Long quantityId = bookQuantityDetails.getQuantityId();
		Long quantity = bookQuantityDetails.getQuantityOfBook();

		User user = userRepository.findUserById(id).orElseThrow(() -> new UserException(401, env.getProperty("104")));
		Book book = bookRepository.findById(bookId).orElseThrow(() -> new UserException(401, env.getProperty("104")));
		double totalprice=book.getBookPrice()*(quantity-1);
		boolean notExist = false;
		for (CartDetails cartt : user.getCartBooks()) {
			/**
			 * checking the number of books available
			 */
			if(!cartt.getBooksList().isEmpty()) {
				
			 notExist = cartt.getBooksList().stream()
					.noneMatch(books -> books.getBookId().equals(bookId));
			 
			 if (!notExist) {

					Quantity quantityDetails = quantityRepository.findById(quantityId).orElseThrow(() -> new UserException(401, env.getProperty("104")));
					quantityDetails.setQuantityOfBook(quantity-1);
					quantityDetails.setTotalprice(totalprice);
					quantityRepository.save(quantityDetails);
					return cartt;
			
					}

		       }
		}

		return null;

	}

	@Transactional
	@Override
	public boolean removeBooksToCart(String token, Long bookId) {

		Long id = jwt.decodeToken(token);

		User user = userRepository.findUserById(id).orElseThrow(() -> new UserException(201, env.getProperty("104")));

		Book book = bookRepository.findById(bookId).orElseThrow(() -> new UserException(201, env.getProperty("104")));

		Quantity quantity = quantityRepository.findById(id)
				.orElseThrow(() -> new UserException(201, env.getProperty("104")));

			
		for (CartDetails cartt : user.getCartBooks()) {
			/**
			 * checking the number of books available
			 */
			boolean notExist = cartt.getBooksList().stream().noneMatch(books -> books.getBookId().equals(bookId));

			if (!notExist) {
	           //cartRepository.deleteCart(cartt.getCartId());		
				cartt.getQuantityOfBooks().remove(quantity);
				cartt.getBooksList().remove(book);
				cartt.getQuantityOfBooks().clear();
				boolean users = userRepository.save(user).getCartBooks() != null ? true : false;
				if (user!=null) {
					return users;
				} 
			}
		}
		return false;
	}

	@Transactional
	@Override
	public boolean verifyBookInCart(String token, Long bookId) {
		if(token != null) {
		List<CartDetails> bookdetails = this.getBooksfromCart(token);
		boolean notExist=false;
		for(CartDetails cart:bookdetails) {
		     notExist = cart.getBooksList().stream().noneMatch(books -> books.getBookId().equals(bookId));
			if (!notExist)
				return true;
		}
		}

		return false;
	}

}
