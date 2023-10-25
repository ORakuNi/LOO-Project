package com.example.loo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.loo.model.comments.Comments;
import com.example.loo.repository.CommentsMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class CommentsService {
	
	private final CommentsMapper commentsMapper;
	
	public void saveComments(Comments comments) {
		commentsMapper.saveComments(comments);
	}

	public Comments findComment(Long comment_id) {
		return commentsMapper.findComment(comment_id);
	}

	public void updateComments(Comments comments) {
		commentsMapper.updateComments(comments);
	}

	public void removeComment(Long comment_id) {
		commentsMapper.removeComment(comment_id);
	}

	public void removeAllComments(Long board_id) {
		commentsMapper.removeAllComments(board_id);
	}

	public List<Comments> findAllComments(Long board_id) {
		return commentsMapper.findAllComments(board_id);
	}
	
	
}
