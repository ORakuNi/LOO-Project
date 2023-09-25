package com.example.loo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.loo.model.board.Board;

@Mapper
public interface BoardMapper {

	void saveBoard(Board board);
	
	List<Board> findAllBoards();
	
	Board findBoard(Long board_id);
	
	void addHit(Long board_id);
	
	void updateBoard(Board board);
	
	void removeBoard(Long board_id);
}
