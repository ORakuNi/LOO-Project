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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.loo.model.board.Board;
import com.example.loo.model.board.BoardCategory;
import com.example.loo.model.board.BoardUpdateForm;
import com.example.loo.model.board.BoardWriteForm;
import com.example.loo.model.comments.Comments;
import com.example.loo.model.comments.CommentsUpdate;
import com.example.loo.model.comments.CommentsWrite;
import com.example.loo.model.member.Member;
import com.example.loo.repository.BoardMapper;
import com.example.loo.repository.CommentsMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("board")
@RequiredArgsConstructor
@Controller
@Slf4j
public class BoardController {
	
	private final BoardMapper boardMapper;
	private final CommentsMapper commentsMapper;
	
	@GetMapping("list")
	public String list(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
						@RequestParam BoardCategory board_category,
						Model model) {
		
		// 로그인 상태가 아니면 로그인 페이지로 보낸다.
        if (loginMember == null) {
            return "redirect:/users/login";
        }
		
        // 데이터베이스에 저장된 모든 Board 객체를 리스트 형태로 받는다.
        List<Board> boards = boardMapper.findAllBoards(board_category);
        
        // Board 리스트를 model 에 저장한다.
        model.addAttribute("boards", boards);
        // 카테고리 정보를 전달할 때 사용
        model.addAttribute("board_category", board_category);
		
		return "board/list";
	}
	
    // 글쓰기 페이지 이동
    @GetMapping("write")
    public String writeForm(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
    						Model model, @RequestParam BoardCategory board_category) {
    	
    	// 로그인 상태가 아니면 로그인 페이지로 보낸다.
        if (loginMember == null) {
            return "redirect:/users/login";
        }
        
        BoardWriteForm boardWriteForm = new BoardWriteForm();
        boardWriteForm.setBoard_category(board_category);
        
        // writeForm.html의 필드 표시를 위해 빈 BoardWriteForm 객체를 생성하여 model 에 저장한다.
        model.addAttribute("writeForm", boardWriteForm);
                
        // board/writeForm.html 을 찾아 리턴한다.
        return "board/write";
    }
    
    // 게시글 쓰기
    @PostMapping("write")
    public String write(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
    					@Validated @ModelAttribute("writeForm") BoardWriteForm boardWriteForm,
                        BindingResult result, RedirectAttributes redirect) {

        // 로그인 상태가 아니면 로그인 페이지로 보낸다.
        if (loginMember == null) {
            return "redirect:/users/login";
        }
        
        log.info("board: {}", boardWriteForm);
        
        // validation 에러가 있으면 board/write.html 페이지를 다시 보여준다.
        if (result.hasErrors()) {
            return "board/write";
        }

        // 파라미터로 받은 BoardWriteForm 객체를 Board 타입으로 변환한다.
        Board board = BoardWriteForm.toBoard(boardWriteForm);
        
        // board 객체에 로그인한 사용자의 아이디와 카테고리 타입 추가
        board.setMember_mail(loginMember.getMember_mail());
        
        // 데이터베이스에 저장한다.
        boardMapper.saveBoard(board);
        
        //다시 redirect 원래 페이지로 돌아오게 할 때?
        redirect.addAttribute("board_category", board.getBoard_category());
        
        // board/list 로 리다이렉트한다.
        return "redirect:/board/list";
    }
    
    @GetMapping("read")
    public String read(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
    					@RequestParam Long board_id,
    					@RequestParam(required = false) Long comment_id,
    					Model model) {
    	
        // 로그인 상태가 아니면 로그인 페이지로 보낸다.
        if (loginMember == null) {
            return "redirect:/users/login";
        }
        log.info("id: {}", board_id);
    	
        // 조회수 1 증가
    	boardMapper.addHit(board_id); 
    	
    	// board_id에 해당하는 게시글 찾기
    	Board board = boardMapper.findBoard(board_id);
    	
    	// board_id에 해당하는 게시글이 없으면 리스트로 리다이렉트 시킨다.
        if (board == null) {
            log.info("게시글 없음");
            return "redirect:/board/list";
        }
        
        // 댓글을 조회한다.
        List<Comments> comments = commentsMapper.findAllComments(board_id);
        log.info("comments: {}", comments);
        
        // 댓글이 있으면 모두 model에 담아 return
        if(!comments.isEmpty() && comments != null) {
        	model.addAttribute("comments", comments);
        }
        
        // 모델에 Board 객체를 저장한다.
        model.addAttribute("board", board);
        model.addAttribute("board_category", board.getBoard_category());
        
        // 새로운 댓글 작성을 받을 model
        model.addAttribute("newComment", new CommentsWrite());
        
        if(comment_id != null) {
        	Comments updateComment = commentsMapper.findComment(comment_id);
        	// 댓글 수정 시 받을 model
        	model.addAttribute("updateComment", updateComment);
        }
        
    	return "board/read";
    }
    
    // 댓글 작성
    @PostMapping("writeComment")
    public String writeComment(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
    							@ModelAttribute("newComment") CommentsWrite commentsWrite,
    							@RequestParam Long board_id,
    							BindingResult results) {
    	
    	if(loginMember == null) {
    		return "redirect:/users/login";
    	}
    	
    	if(results.hasErrors()) {
    		return "redirect:/board/read?board_id=" + board_id;
    	}
    	
    	Comments comments = commentsWrite.toComments(commentsWrite);
    	comments.setBoard_id(board_id);
    	comments.setMember_mail(loginMember.getMember_mail());
    	commentsMapper.saveComments(comments);
    	
    	return "redirect:/board/read?board_id=" + board_id;
    }
    
    // 댓글 수정
    @GetMapping("updateComment")
    public String updateComment(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
								@RequestParam Long comment_id,
								Model model) {
    	
    	if(loginMember == null) {
    		return "redirect:/users/login";
    	}
    	
    	Comments updateComment = commentsMapper.findComment(comment_id);
    	
    	if(updateComment == null || !updateComment.getMember_mail().equals(loginMember.getMember_mail())) {
    		log.info("댓글 수정 권한 없음");
    		return "redirect:/users/login";
    	}
    	
    	Long board_id = updateComment.getBoard_id();
    	
    	return "redirect:/board/read?board_id=" + board_id + "&comment_id=" + updateComment.getComment_id();
    	
    }
    
    // 댓글 수정 저장
    @PostMapping("updateComment")
    public String updateComment(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
    							@RequestParam Long comment_id,
    							@Validated @ModelAttribute("updateComment") CommentsUpdate commentsUpdate,
    							BindingResult result) {
    	// 로그인 상태가 아니면 로그인 페이지로 보낸다.
        if (loginMember == null) {
            return "redirect:/users/login";
        }
        
        log.info("commentsUpdate: {}", commentsUpdate);
        
        Comments comments = commentsMapper.findComment(comment_id);
    	
        // Board 객체가 없거나 작성자가 로그인한 사용자의 아이디와 다르면 수정하지 않고 리스트로 리다이렉트 시킨다.
        if (comments == null || !comments.getMember_mail().equals(loginMember.getMember_mail())) {
            log.info("수정 권한 없음");
            return "redirect:/board/read";
        }
        
        // 제목과 내용 수정
        comments.setComment_contents(commentsUpdate.getComment_contents());
        
        // 수정한 Board 를 데이터베이스에 update 한다.
        commentsMapper.updateComments(comments);
        
        Long board_id = comments.getBoard_id();
    	
    	return "redirect:/board/read?board_id=" + board_id;
    }
    
    
    // 댓글 삭제
    @GetMapping("deleteComment")
    public String deleteComment(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
    							@RequestParam Long comment_id) {
    	
        // 로그인 상태가 아니면 로그인 페이지로 보낸다.
        if (loginMember == null) {
            return "redirect:/users/login";
        }
        
        // board_id 에 해당하는 게시글을 가져온다.
        Comments comments = commentsMapper.findComment(comment_id);
        
        // 게시글이 존재하지 않거나 작성자와 로그인 사용자의 아이디가 다르면 리스트로 리다이렉트 한다.
        if (comments == null || !comments.getMember_mail().equals(loginMember.getMember_mail())) {
            log.info("삭제 권한 없음");
            return "redirect:/board/read";
        }
    	
        // 댓글 삭제
    	commentsMapper.removeComment(comment_id);
    	
    	Long board_id = comments.getBoard_id();
    	
    	return "redirect:/board/read?board_id=" + board_id;
    }
    
    // 게시글 수정 페이지 이동
    @GetMapping("update")
    public String updateForm(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
    						@RequestParam Long board_id,
    						@RequestParam BoardCategory board_category,
            				Model model) {
    	
        // 로그인 상태가 아니면 로그인 페이지로 보낸다.
        if (loginMember == null) {
            return "redirect:/users/login";
        }

    	Board board = boardMapper.findBoard(board_id);
    	// board_id에 해당하는 게시글이 없거나
    	// 게시글의 작성자가 로그인한 사용자의 아이디와 다르면 수정하지 않고 리스트로 리다이렉트 시킨다.
        if (board == null || !board.getMember_mail().equals(loginMember.getMember_mail())) {
            log.info("수정 권한 없음");
            return "redirect:/board/list";
        }
        
        // model 에 board 객체를 저장한다.
    	model.addAttribute("board", board);
    	
    	return "board/update";
    }
    
    @PostMapping("update")
    public String update(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
    					@RequestParam Long board_id,
    					@Validated @ModelAttribute("board") BoardUpdateForm updateBoard,
    					BindingResult result, RedirectAttributes redirect) {
    	
        // 로그인 상태가 아니면 로그인 페이지로 보낸다.
        if (loginMember == null) {
            return "redirect:/member/login";
        }
        
        log.info("board: {}", updateBoard);
    	
    	// validation 에 에러가 있으면 board/update.html 페이지로 돌아간다.
        if (result.hasErrors()) {
            return "board/update";
        }
        
        Board board = boardMapper.findBoard(board_id);
    	
        // Board 객체가 없거나 작성자가 로그인한 사용자의 아이디와 다르면 수정하지 않고 리스트로 리다이렉트 시킨다.
        if (board == null || !board.getMember_mail().equals(loginMember.getMember_mail())) {
            log.info("수정 권한 없음");
            return "redirect:/board/list";
        }
        
        // 제목과 내용 수정
        board.setBoard_title(updateBoard.getBoard_title());
        board.setBoard_contents(updateBoard.getBoard_contents());
        
        // 수정한 Board 를 데이터베이스에 update 한다.
        boardMapper.updateBoard(board);
        
        redirect.addAttribute("board_category", board.getBoard_category());
        
    	return "redirect:/board/list";
    }
    
    // 게시글 삭제
    @GetMapping("delete")
    public String delete(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
    					@RequestParam Long board_id, RedirectAttributes redirect) {
    	
        // 로그인 상태가 아니면 로그인 페이지로 보낸다.
        if (loginMember == null) {
            return "redirect:/users/login";
        }
        
        // board_id 에 해당하는 게시글을 가져온다.
        Board board = boardMapper.findBoard(board_id);
        
        // 게시글이 존재하지 않거나 작성자와 로그인 사용자의 아이디가 다르면 리스트로 리다이렉트 한다.
        if (board == null || !board.getMember_mail().equals(loginMember.getMember_mail())) {
            log.info("삭제 권한 없음");
            return "redirect:/board/list";
        }
    	
        // 댓글 삭제
        commentsMapper.removeAllComments(board_id);
        // 게시글 삭제
    	boardMapper.removeBoard(board_id);
    	
    	redirect.addAttribute("board_category", board.getBoard_category());
    	
    	return "redirect:/board/list";
    }
}