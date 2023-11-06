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
	
	// 댓글 작성
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
	
	// 게시물에 해당하는 댓글 가져오기
	@GetMapping("{board_id}")
	public ResponseEntity<List<Comments>> readComments(@PathVariable Long board_id){
		List<Comments> comments = commentsMapper.findAllComments(board_id);
		
		return ResponseEntity.ok(comments);
	}
	
	// 댓글 삭제
	@DeleteMapping("{comment_id}")
	public ResponseEntity<String> removeComment(@SessionAttribute("loginMember") Member loginMember,
												@PathVariable Long comment_id){
		Comments findComment = commentsMapper.findComment(comment_id);
		// 댓글 작성자와 로그인한 회원이 같거나 로그인한 회원이 관리자일 경우 삭제 진행 후 ok status return
		if(findComment.getMember_mail().equals(loginMember.getMember_mail())||loginMember.getPosition_id().equals("manager")) {
			commentsMapper.removeComment(comment_id);
			
			return ResponseEntity.ok("remove ok");
		}
		// 위 if구문에 해당안되는 경우 400에러 반환
		return ResponseEntity.badRequest().build();
	}
	
	// 댓글 수정
	@PutMapping("{comment_id}")
	public ResponseEntity<Comments> updateComment(@SessionAttribute("loginMember") Member loginMember,
												@PathVariable Long comment_id,
												@ModelAttribute Comments comments){
		Comments findComment = commentsMapper.findComment(comment_id);
		// 댓글 작성자와 로그인한 회원이 다를 경우 수정하기 요청 시 400에러 반환
		if(!findComment.getMember_mail().equals(loginMember.getMember_mail())) {
			return ResponseEntity.badRequest().build();
		}
		
		findComment.setComment_contents(comments.getComment_contents());
		
		commentsMapper.updateComments(findComment);
		
		return ResponseEntity.ok(findComment);
	}
	
	// 동호회 댓글 좋아요
	@GetMapping("like/{comment_id}")
	public ResponseEntity<String> like(@PathVariable Long comment_id){
		commentsMapper.like(comment_id);
		
		return ResponseEntity.ok("like ok");
	}
	

}
