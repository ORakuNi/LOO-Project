package com.example.loo.model.board;

import javax.validation.constraints.NotBlank;



import lombok.Data;

@Data
public class BoardWriteForm {

	@NotBlank
	private String board_title; //글 제목
	@NotBlank
	private String board_contents; //내용
	private BoardCategory board_category; // 카테고리 추가
	
	public static Board toBoard(BoardWriteForm boardWriteForm) {
		Board board = new Board();
		board.setBoard_title(boardWriteForm.getBoard_title());
		board.setBoard_contents(boardWriteForm.getBoard_contents());
		board.setBoard_category(boardWriteForm.getBoard_category());
		return board;
	}
}