package com.example.loo.model.board;

import javax.validation.constraints.NotBlank;



import lombok.Data;

@Data
public class BoardWriteForm {

	@NotBlank
	private String board_title; //글 제목
	@NotBlank
	private String contents; //내용
	
	public static Board toBoard(BoardWriteForm boardWriteForm) {
		Board board = new Board();
		board.setBoard_title(boardWriteForm.getBoard_title());
		board.setContents(boardWriteForm.getContents());
		return board;
	}
}
