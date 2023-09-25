package com.example.loo.model.board;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Board {
	
	private Long board_id; //게시물 아이디
	private String board_title; //글 제목
	private String contents; //내용
	private Long member_id; //작성자
	private String board_category; // 게시판 종류
	private LocalDateTime created_time; //작성일
	private LocalDateTime edit_time;  // 수정시간
	private Long hit; //조회수
	

}
