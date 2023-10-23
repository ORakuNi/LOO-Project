package com.example.loo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.loo.model.board.Board;
import com.example.loo.model.board.BoardCategory;
import com.example.loo.model.file.BoardAttachedFile;

@Mapper
public interface BoardMapper {
	

	void saveBoard(Board board);
	
	List<Board> findAllBoards(BoardCategory board_category);
		
	Board findBoard(Long board_id);
	
	void addHit(Long board_id);
	
	void updateBoard(Board updateBoard);
	
	void removeBoard(Long board_id);
	//첨부파일 저장
	void saveFile(BoardAttachedFile attachedFile);
	//게시글 아이디로 첨부파일 검색
	BoardAttachedFile findFileByBoardId(Long board_id);
	//첨부파일 아이디로 첨부파일 검색
	BoardAttachedFile findFileByAttachedFileId(Long attachedFile_id);
	//첨부파일 삭제
	void removeAttachedFile(Long attachedFile_id);
	
	List<BoardAttachedFile> findFiles();

	List<Board> findBoards(@Param("searchText") String searchText, @Param("board_category") BoardCategory board_category);
}
