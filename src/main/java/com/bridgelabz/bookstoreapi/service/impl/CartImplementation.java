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
		long quantity = 1;
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
		CartDetails cart = new CartDetails();
		ArrayList<Book> booklist = new ArrayList<>();
		ArrayList<Quantity> qt = new ArrayList<Quantity>();
		Quantity qunatityofbook = new Quantity(quantity);
		if (books == null) {
			booklist.add(book);
			cart.setPlaceTime(LocalDateTime.now());
			cart.setBooksList(booklist);
			qt.add(qunatityofbook);
			cart.setQuantityOfBooks(qt);
			user.getCartBooks().add(cart);
			return userRepository.save(user).getCartBooks();
		}
		/**
		 * Checking whether book is already present r not
		 */
		Optional<Book> cartbook = books.stream().filter(t -> t.getBookId() == bookId).findFirst();

		if (cartbook.isPresent()) {
			throw new UserException(401, env.getProperty("505"));
		} else {
			booklist.add(book);
			cart.setPlaceTime(LocalDateTime.now());
			cart.setBooksList(booklist);
			qt.add(qunatityofbook);
			cart.setQuantityOfBooks(qt);
			user.getCartBooks().add(cart);
		}

		return userRepository.save(user).getCartBooks();

	}

	@Transactional
	@Override
	public List<CartDetails> addBooksQuantityInCart(String token, Long bookId, CartdetailsDto bookQuantityDetails) {

		Long id = jwt.decodeToken(token);
		Long quantityId = bookQuantityDetails.getQuantityId();
		Long quantity = bookQuantityDetails.getQuantityOfBook();

		User user = userRepository.findUserById(id).orElseThrow(() -> new UserException(401, env.getProperty("104")));

		for (CartDetails cartt : user.getCartBooks()) {
			/**
			 * checking the number of books available
			 */
			if(!cartt.getBooksList().isEmpty()) {
				
			boolean notExist = cartt.getBooksList().stream()
					.noneMatch(books -> books.getBookId().equals(bookId) && books.getNoOfBooks() > quantity);
			
			ArrayList<Quantity> qt = new ArrayList<Quantity>();
			if (!notExist) {

				for (Quantity qant : cartt.getQuantityOfBooks()) {
					if (qant.getQuantityId().equals(quantityId)) {

						Quantity qunatityofbook = new Quantity(quantity + 1);

						qt.add(qunatityofbook);
						cartt.setQuantityOfBooks(qt);
						return userRepository.save(user).getCartBooks();

					}
				}

			}else {
				throw new UserException(401, env.getProperty("506"));
			}
		}
		}

		return null;

	}
	
	@Transactional
	@Override
	public List<CartDetails> descBooksQuantityInCart(String token, Long bookId, CartdetailsDto bookQuantityDetails) {

		Long id = jwt.decodeToken(token);
		Long quantityId = bookQuantityDetails.getQuantityId();
		Long quantity = bookQuantityDetails.getQuantityOfBook();

		User user = userRepository.findUserById(id).orElseThrow(() -> new UserException(401, env.getProperty("104")));

		for (CartDetails cartt : user.getCartBooks()) {
			/**
			 * checking the number of books available
			 */
			if(!cartt.getBooksList().isEmpty()) {
				
			boolean notExist = cartt.getBooksList().stream()
					.noneMatch(books -> books.getBookId().equals(bookId));
			
			ArrayList<Quantity> qt = new ArrayList<Quantity>();
			if (!notExist) {

				for (Quantity qant : cartt.getQuantityOfBooks()) {
					if (qant.getQuantityId().equals(quantityId)) {

						Quantity qunatityofbook = new Quantity(quantity - 1);

						qt.add(qunatityofbook);
						cartt.setQuantityOfBooks(qt);
						return userRepository.save(user).getCartBooks();

					}
				}

			}else {
				throw new UserException(401, env.getProperty("506"));
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

				cartt.getQuantityOfBooks().remove(quantity);
				cartt.getBooksList().remove(book);
				cartt.getQuantityOfBooks().clear();
				boolean users = userRepository.save(user).getCartBooks() != null ? true : false;
				if (users) {
					return users;
				} else {
					throw new UserException(401, env.getProperty("4041"));
				}

			}
		}
		return false;
	}

	@Transactional
	@Override
	public boolean verifyBookInCart(String token, Long bookId) {

		this.getBooksfromCart(token).forEach((cart) -> {
			boolean notExist = cart.getBooksList().stream().noneMatch(books -> books.getBookId().equals(bookId));
			if (notExist)
				throw new UserException(401, env.getProperty("506"));

		});
		return true;
	}

}
