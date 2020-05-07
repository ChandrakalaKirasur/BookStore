package com.bridgelabz.bookstoreapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import com.bridgelabz.bookstoreapi.dto.RatingReviewDTO;

@Entity
@Table(name="review_and_rating")
public class ReviewAndRating {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ratingReviewId;

	@Column(name = "rating", nullable = false)
	private Integer rating;
	
	@Column(name = "review", nullable = false)
	private String review;
	
	@OneToOne
	@JoinTable(name="rating_review_user", joinColumns = @JoinColumn(name="ratingReviewId"),
	inverseJoinColumns = @JoinColumn(name="user_id"))
	private User user;
	
	public ReviewAndRating() {
	}

	public ReviewAndRating(RatingReviewDTO rrDTO) {
		this.rating = rrDTO.getRating();
		this.review = rrDTO.getReview();
	}
	
	public Long getRatingReviewId() {
		return ratingReviewId;
	}

	public void setRatingReviewId(long ratingReviewId) {
		this.ratingReviewId = ratingReviewId;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setRatingReviewId(Long ratingReviewId) {
		this.ratingReviewId = ratingReviewId;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}
	
}
