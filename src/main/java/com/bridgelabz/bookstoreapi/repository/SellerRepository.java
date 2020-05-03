package com.bridgelabz.bookstoreapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bridgelabz.bookstoreapi.entity.Seller;

public interface SellerRepository extends JpaRepository<Seller, Long>{

	Optional<Seller> findByEmailAddress(String emailAddress);
	Optional<Seller> findByMobile(Long mobile);
	@Query(value=" select * from seller where email_address=?",nativeQuery=true)
	Seller getUser(String email);
	
}
