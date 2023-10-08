package com.example.loo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.loo.model.board.Board;
import com.example.loo.model.board.BoardCategory;
import com.example.loo.repository.BoardMapper;

@Controller
public class HomeController {
	
	private final BoardMapper boardMapper;
	
	@Autowired
    public HomeController(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }

	@GetMapping("/")
	public String home(Model model) {
		
        // 데이터베이스에 저장된 모든 Board 객체를 리스트 형태로 받는다.
        List<Board> boards = boardMapper.findAllBoards(BoardCategory.NOTICE);
        // Board 리스트를 model 에 저장한다.
        model.addAttribute("boards", boards);
        
		return "index";
	}
	
	
}
