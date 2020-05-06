package com.bridgelabz.bookstoreapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.bookstoreapi.entity.QuantityOfBooks;

public interface QuantityOfBooksRepository extends JpaRepository<QuantityOfBooks, Long> {

}
