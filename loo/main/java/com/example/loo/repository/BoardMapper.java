package com.example.loo.repository;

import java.util.List;


import org.apache.ibatis.annotations.Mapper;

import com.example.loo.model.board.AttachedFile;
import com.example.loo.model.board.Board;
import com.example.loo.model.board.BoardCategory;

@Mapper
public interface BoardMapper {
	

	void saveBoard(Board board);
	
	List<Board> findAllBoards(BoardCategory board_category);
		
	Board findBoard(Long board_id);
	
	void addHit(Long board_id);
	
	void updateBoard(Board updateBoard);
	
	void removeBoard(Long board_id);
	//첨부파일 저장
	void saveFile(AttachedFile attachedFile);
	//게시글 아이디로 첨부파일 검색
	AttachedFile findFileByBoardId(Long board_id);
	//첨부파일 아이디로 첨부파일 검색
	AttachedFile findFileByAttachedFileId(Long attachedFile_id);
	//첨부파일 삭제
	void removeAttachedFile(Long attachedFile_id);
}
