package com.example.loo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.loo.model.board.Board;
import com.example.loo.model.board.BoardCategory;
import com.example.loo.model.file.AttachedFile;
import com.example.loo.model.file.BoardAttachedFile;

@Mapper
public interface BoardMapper {
	
	void saveBoard(Board board);
	
	List<Board> findAllBoards(BoardCategory board_category);
		
	Board findBoard(Long board_id);
	
	void addHit(Long board_id);
	
	void updateBoard(Board board);
	
	void removeBoard(Long board_id);
	
	void saveFile(AttachedFile attachedFile);
	
    List<BoardAttachedFile> findFiles();
    
}
