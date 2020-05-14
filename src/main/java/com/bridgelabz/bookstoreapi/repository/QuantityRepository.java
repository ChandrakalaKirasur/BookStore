package com.bridgelabz.bookstoreapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.bookstoreapi.entity.Quantity;

public interface QuantityRepository extends JpaRepository<Quantity, Long>{

}
