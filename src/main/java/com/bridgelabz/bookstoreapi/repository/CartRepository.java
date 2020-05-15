package com.bridgelabz.bookstoreapi.repository;

import org.springframework.data.repository.CrudRepository;

import com.bridgelabz.bookstoreapi.entity.CartDetails;

public interface CartRepository extends CrudRepository<CartDetails,Long> {

}
