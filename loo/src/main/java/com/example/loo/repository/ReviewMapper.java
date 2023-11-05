package com.example.loo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.loo.model.review.Review;

@Mapper
public interface ReviewMapper {

	void saveReview(Review review);
	
	List<Review> findAllReview(Long matgip_num);
	
	Review findReview(Long review_id);
	
	void updateReview(Review review);
	
	void removeReview(Long review_id);
	
	void removeAllReview(Long matgip_id);
	
	void like(Long matgip_num);
	
}
