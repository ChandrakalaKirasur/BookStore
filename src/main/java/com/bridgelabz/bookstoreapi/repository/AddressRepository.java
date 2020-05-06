package com.bridgelabz.bookstoreapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.bookstoreapi.entity.Address;
import com.bridgelabz.bookstoreapi.entity.User;
@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{
	@Query(value = "select * from user where user_id=?", nativeQuery = true)
	User findUserById(Long uId);
	@Query(value = "select * from Address where address_id=?", nativeQuery = true)
	List<Address> findAddressByUserId(Long addressId);
	@Query(value = "select * from Address where address_id=?", nativeQuery = true)
	Address findAddressById(Long id);

}
