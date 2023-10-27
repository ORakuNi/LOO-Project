package com.example.loo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.loo.model.board.Comments;
import com.example.loo.model.member.Member;
import com.example.loo.repository.CommentsMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("comments")
@Slf4j
public class CommentsRestController {
	
	private final CommentsMapper commentsMapper;
	
	@PostMapping("{board_id}")
	public ResponseEntity<String> writeComment(@PathVariable Long board_id,
											@ModelAttribute Comments comments,
											@SessionAttribute("loginMember") Member loginMember){
		log.info("comment:{}", comments);
		
		comments.setBoard_id(board_id);
		comments.setMember_mail(loginMember.getMember_mail());
		
		commentsMapper.saveComments(comments);
		
		return ResponseEntity.ok("write ok");
	}
	
	@GetMapping("{board_id}")
	public ResponseEntity<List<Comments>> readComments(@PathVariable Long board_id){
		List<Comments> comments = commentsMapper.findAllComments(board_id);
		
		return ResponseEntity.ok(comments);
	}
	
	@DeleteMapping("{comment_id}")
	public ResponseEntity<String> removeComment(@SessionAttribute("loginMember") Member loginMember,
												@PathVariable Long comment_id){
		Comments findComment = commentsMapper.findComment(comment_id);
		if(!findComment.getMember_mail().equals(loginMember.getMember_mail())) {
			return ResponseEntity.badRequest().build();
		}
		
		commentsMapper.removeComment(comment_id);
		
		return ResponseEntity.ok("remove ok");
	}
	
	@PutMapping("{comment_id}")
	public ResponseEntity<Comments> updateComment(@SessionAttribute("loginMember") Member loginMember,
												@PathVariable Long comment_id,
												@ModelAttribute Comments comments){
		Comments findComment = commentsMapper.findComment(comment_id);
		if(!findComment.getMember_mail().equals(loginMember.getMember_mail())) {
			return ResponseEntity.badRequest().build();
		}
		
		findComment.setComment_contents(comments.getComment_contents());
		
		commentsMapper.updateComments(findComment);;
		
		return ResponseEntity.ok(findComment);
	}
	
	@GetMapping("like/{comment_id}")
	public ResponseEntity<String> like(@PathVariable Long comment_id){
		commentsMapper.like(comment_id);
		
		return ResponseEntity.ok("like ok");
	}
	

}
