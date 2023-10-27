package com.example.loo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.loo.model.board.Comments;

@Mapper
public interface CommentsMapper {
	
	void saveComments(Comments comments);
	
	List<Comments> findAllComments(Long board_id);
	
	Comments findComment(Long comment_id);
	
	void updateComments(Comments comments);
	
	void removeComment(Long comment_id);
	
	void removeAllComments(Long board_id);
	
	void like(Long board_id);
}
