package com.bridgelabz.bookstoreapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bridgelabz.bookstoreapi.entity.Admin;

public interface AdminRepository extends CrudRepository<Admin, Long> {
	boolean existsByName(String name);

	@Query(value = "select * from admin where admin_email=?", nativeQuery = true)
	Optional<Admin> findByEmail(String email);

	@Query(value = "select * from admin where admin_id=?", nativeQuery = true)
	Optional<Admin> findByAdminId(Long adminId);
}
