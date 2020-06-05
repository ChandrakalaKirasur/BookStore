package com.bridgelabz.bookstoreapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bridgelabz.bookstoreapi.entity.CartDetails;
import com.bridgelabz.bookstoreapi.entity.User;

public interface CartRepository extends JpaRepository<CartDetails,Long> {

	@Query(value = "delete from cart_details where cart_id=?", nativeQuery = true)
	void deleteCart(Long cart_id);
}
