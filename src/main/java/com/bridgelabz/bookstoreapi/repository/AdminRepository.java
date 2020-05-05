package com.bridgelabz.bookstoreapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bridgelabz.bookstoreapi.entity.Admin;

public interface AdminRepository extends CrudRepository<Admin, Long> {
	@Query(value = "select * from admin where name=?", nativeQuery = true)
	boolean existsByFirstName(String name);

	@Query(value = "select * from admin where email=?", nativeQuery = true)
	Optional<Admin> findByEmail(String emailId);

	@Query(value = "select * from admin where admin_id=?", nativeQuery = true)
	Optional<Admin> findByAdminId(Long adminId);
}
