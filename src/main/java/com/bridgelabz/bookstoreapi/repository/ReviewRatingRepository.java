package com.bridgelabz.bookstoreapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.bookstoreapi.entity.ReviewAndRating;

public interface ReviewRatingRepository extends JpaRepository<ReviewAndRating, Long>{

}
