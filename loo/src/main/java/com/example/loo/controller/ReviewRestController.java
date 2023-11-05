package com.example.loo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.loo.model.member.Member;
import com.example.loo.model.review.Review;
import com.example.loo.repository.ReviewMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("review")
@Slf4j
public class ReviewRestController {

	
	private final ReviewMapper reviewMapper;
	
	// 맛집의 고유번호로 리뷰가져오기
	@GetMapping("{matgip_num}")
	public ResponseEntity<List<Review>> readReview(@PathVariable Long matgip_num){
		
		List<Review> review = reviewMapper.findAllReview(matgip_num);
		log.info("id : {}" , matgip_num);
		return ResponseEntity.ok(review);
	}
	
	// 리뷰 쓰기
	@PostMapping("{matgip_num}")
	public ResponseEntity<String> writeReview(@PathVariable Long matgip_num, @ModelAttribute Review review,
											  @SessionAttribute("loginMember") Member loginMember){
		
		review.setMember_mail(loginMember.getMember_mail());
		
		reviewMapper.saveReview(review);
		
		return ResponseEntity.ok("write ok");
	}

	// 리뷰 삭제
	@DeleteMapping("{review_id}")
	public ResponseEntity<String> removeReview(@SessionAttribute("loginMember") Member loginMember, 
											   @PathVariable Long review_id){
		
		Review findReview = reviewMapper.findReview(review_id);
		
		if(!findReview.getMember_mail().equals(loginMember.getMember_mail()) || loginMember.getPosition_id().equals("manager")) {
			reviewMapper.removeReview(review_id);
			return ResponseEntity.ok("삭제완료");
		}
		return ResponseEntity.badRequest().build();
	}
	
	// 리뷰 수정
	@PostMapping("{review_id}")
	public ResponseEntity<Review> updateReview(@SessionAttribute("loginMember") Member loginMember, 
											   @PathVariable Long review_id, @ModelAttribute Review review){
		Review findReview = reviewMapper.findReview(review_id);
		if(!findReview.getMember_mail().equals(loginMember.getMember_mail())) {
			return ResponseEntity.badRequest().build();
		}
		findReview.setReview_contents(review.getReview_contents());
		
		reviewMapper.updateReview(findReview);
		
		return ResponseEntity.ok(findReview);
	}
	
	// 리뷰 공감하기
	@GetMapping("like/{review_id}")
	public ResponseEntity<String> like(@PathVariable Long review_id){
		reviewMapper.like(review_id);
		return ResponseEntity.ok("공감완료");
			
	}
	
	
	
	
}
