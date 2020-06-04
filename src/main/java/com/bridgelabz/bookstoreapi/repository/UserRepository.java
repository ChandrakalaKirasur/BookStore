package com.bridgelabz.bookstoreapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.jpa.repository.Query;

import com.bridgelabz.bookstoreapi.entity.CartDetails;
import com.bridgelabz.bookstoreapi.entity.Seller;
import com.bridgelabz.bookstoreapi.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{

	@Query(value = "select * from user where user_id=?", nativeQuery = true)
    String login();

//	@Query(value = "insert into user (user_id,user_name,user_email, password, create_time, user_number) values (?, ?, ?, ?, ?, ?, ?)", nativeQuery = true)
//	User register(User user);

	@Query(value = "update user set password=? where email=?", nativeQuery = true)
	User forgotPassword(String password, String email);

	@Query(value = "select * from user where user_id=?", nativeQuery = true)
	Optional<User> findUserById(Long id);

	@Query(value = "select * from user where email=?", nativeQuery = true)
    Optional<User> findUserByEmail(String email);

	@Query(value = "update user set verify_status=true where user_id=?", nativeQuery = true)
	boolean verify(long id);

	@Query(value = "select * from user where user_number=?", nativeQuery = true)
	Optional<User> findByMobile(Long mbl);
	
	@Query(value = "select * from user where email=?", nativeQuery = true)
	Optional<User> findByEmail(@Valid String email);
	@Query(value = "select user_user_id from user_whilist_books where whilist_books_book_id=?", nativeQuery = true)
	List<Long> getUserForNotify(Long bookId);
	
//   @Query(value="delete from cart_details where cart_id=?;",nativeQuery=true)
//   CartDetails deleteCart(Long id);

}
