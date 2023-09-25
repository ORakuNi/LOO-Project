package com.example.loo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.loo.model.board.Board;
import com.example.loo.model.board.BoardUpdateForm;
import com.example.loo.model.board.BoardWriteForm;
import com.example.loo.repository.BoardMapper;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("board")
@Controller
@Slf4j
public class BoardController {
	
	private final BoardMapper boardMapper;
	
	@Autowired
    public BoardController(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }
	
	@GetMapping("test")
	public String test() {
		
		return "board/test";
	}
	
	@GetMapping("index2")
	public String indext2() {
		
		return "board/index2";
	}
    

	@GetMapping("board")
	public String baord() {
		
		return "board/board";
	}
	
	
	@GetMapping("list")
	public String list(Model model) {
		
        // 데이터베이스에 저장된 모든 Board 객체를 리스트 형태로 받는다.
        List<Board> boards = boardMapper.findAllBoards();
        // Board 리스트를 model 에 저장한다.
        model.addAttribute("boards", boards);
		
		return "board/list";
	}
	
    // 글쓰기 페이지 이동
    @GetMapping("write")
    public String writeForm(Model model) {

        // writeForm.html의 필드 표시를 위해 빈 BoardWriteForm 객체를 생성하여 model 에 저장한다.
        model.addAttribute("writeForm", new BoardWriteForm());
        // board/writeForm.html 을 찾아 리턴한다.
        return "board/write";
    }
    
    // 게시글 쓰기
    @PostMapping("write")
    public String write(@Validated @ModelAttribute("writeForm") BoardWriteForm boardWriteForm,
                        BindingResult result) {


        log.info("board: {}", boardWriteForm);
        // validation 에러가 있으면 board/write.html 페이지를 다시 보여준다.
        if (result.hasErrors()) {
            return "board/write";
        }

        // 파라미터로 받은 BoardWriteForm 객체를 Board 타입으로 변환한다.
        Board board = BoardWriteForm.toBoard(boardWriteForm);

        // 데이터베이스에 저장한다.
        
        board.setBoard_category("notice");
        boardMapper.saveBoard(board);
        
        // board/list 로 리다이렉트한다.
        return "redirect:/board/list";
    }
    
    @GetMapping("read")
    public String read(@RequestParam Long board_id, Model model) {
    	
    	boardMapper.addHit(board_id);
    	Board board = boardMapper.findBoard(board_id);
    	
    	// board_id에 해당하는 게시글이 없으면 리스트로 리다이렉트 시킨다.
        if (board == null) {
            log.info("게시글 없음");
            return "redirect:/board/list";
        }
        
        // 모델에 Board 객체를 저장한다.
        model.addAttribute("board", board);
        
    	
    	return "board/read";
    }
    
    // 게시글 수정 페이지 이동
    @GetMapping("update")
    public String updateForm(@RequestParam Long board_id,
            		Model model) {
    	
    	Board board = boardMapper.findBoard(board_id);
    	
    	model.addAttribute("board", board);
    	
    	return "board/update";
    }
    
    @PostMapping("update")
    public String update(@RequestParam Long board_id,
            @Validated @ModelAttribute("board") BoardUpdateForm updateBoard,
            BindingResult result) {
    	
        if (result.hasErrors()) {
            return "board/update";
        }
        
        Board board = boardMapper.findBoard(board_id);
    	
        board.setBoard_title(updateBoard.getBoard_title());
        board.setContents(updateBoard.getContents());
        
        boardMapper.updateBoard(board);
        
    	return "redirect:/board/list";
    }
    
    @GetMapping("delete")
    public String delete(@RequestParam Long board_id) {
    	
    	boardMapper.removeBoard(board_id);
    	
    	return "redirect:/board/list";
    }
    
    
}










