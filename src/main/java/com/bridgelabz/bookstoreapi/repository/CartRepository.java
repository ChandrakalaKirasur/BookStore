package com.bridgelabz.bookstoreapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.bookstoreapi.entity.CartDetails;

public interface CartRepository extends JpaRepository<CartDetails, Long>{

}
