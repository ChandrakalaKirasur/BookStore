package com.bridgelabz.bookstoreapi.dto;

public class RatingReviewDTO {

	private Integer rating;
	
	private String review;
	
	public RatingReviewDTO() {
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}
}
