package com.example.loo.model.board;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class BoardWriteForm {

	@NotBlank(message = "제목을 입력해 주세요.")
    @Size(max = 50, message = "제목은 50자 이내로 입력해 주세요.")
	private String board_title; //글 제목
	@NotBlank(message = "내용을 입력해 주세요.")
	@Size(max = 4000, message = "내용은 4000자 이내로 입력해 주세요.")
	private String board_contents; //내용
	private BoardCategory board_category; // 카테고리 추가
	private MultipartFile attachedFile;
	
	
	
	public Board toBoard(BoardWriteForm boardWriteForm) {
		Board board = new Board();
		board.setBoard_title(boardWriteForm.getBoard_title());
		board.setBoard_contents(boardWriteForm.getBoard_contents());
		board.setBoard_category(boardWriteForm.getBoard_category());
		return board;
	}
}
