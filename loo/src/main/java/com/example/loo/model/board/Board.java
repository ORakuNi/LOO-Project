package com.example.loo.model.board;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Board {
	
	private Long board_id; //게시물 아이디
	private String board_title; //글 제목
	private String board_contents; //내용
	private	String member_mail; //작성자 메일
	private	String member_name; //작성자 이름
	private BoardCategory board_category; // 게시판 종류
	private LocalDateTime create_time; //작성일
	private LocalDateTime edit_time;  // 수정시간
	private Long hit; //조회수
	private String saved_filename;

    public static BoardUpdateForm toBoardUpdateForm(Board board) {
        BoardUpdateForm boardUpdateForm = new BoardUpdateForm();
        boardUpdateForm.setBoard_id(board.getBoard_id());
        boardUpdateForm.setBoard_title(board.getBoard_title());
        boardUpdateForm.setBoard_contents(board.getBoard_contents());
        boardUpdateForm.setMember_mail(board.getMember_mail());
        boardUpdateForm.setMember_name(board.getMember_name());
        boardUpdateForm.setHit(board.getHit());
        boardUpdateForm.setBoard_category(board.getBoard_category());
        boardUpdateForm.setCreate_time(board.getCreate_time());
        return boardUpdateForm;
    }

}
