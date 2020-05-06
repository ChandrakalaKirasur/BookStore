package com.bridgelabz.bookstoreapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bridgelabz.bookstoreapi.entity.CartDetails;
import com.bridgelabz.bookstoreapi.entity.User;

public interface CartRepository extends JpaRepository<CartDetails, Long>{

	@Query(value = "delete from cart_details where user_id=?", nativeQuery = true)
	Optional<CartDetails> deleteById(long id);
}
