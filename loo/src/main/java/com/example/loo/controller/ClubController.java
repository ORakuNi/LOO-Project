package com.example.loo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.multipart.MultipartFile;

import com.example.loo.model.board.Board;
import com.example.loo.model.board.BoardCategory;
import com.example.loo.model.board.BoardUpdateForm;
import com.example.loo.model.board.BoardWriteForm;
import com.example.loo.model.comments.Comments;
import com.example.loo.model.comments.CommentsUpdate;
import com.example.loo.model.comments.CommentsWrite;
import com.example.loo.model.file.BoardAttachedFile;
import com.example.loo.model.member.Member;
import com.example.loo.service.BoardService;
import com.example.loo.service.CommentsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("club")
public class ClubController {
	
	private final BoardService boardService;
	private final CommentsService commentsService;
	@Value("${file.upload.path}")
	private String uploadPath;
	
    // 댓글 작성
    @PostMapping("writeComment")
    public String writeComment(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
    							@ModelAttribute("newComment") CommentsWrite commentsWrite,
    							@RequestParam Long board_id,
    							BindingResult results) {
    	if(results.hasErrors()) {
    		return "redirect:/club/chat?board_id=" + board_id;
    	}
    	
    	Comments comments = commentsWrite.toComments(commentsWrite);
    	comments.setBoard_id(board_id);
    	comments.setMember_mail(loginMember.getMember_mail());
    	commentsService.saveComments(comments);
    	
    	return "redirect:/club/chat?board_id=" + board_id;
    }
    
    // 댓글 수정
    @GetMapping("updateComment")
    public String updateComment(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
								@RequestParam Long comment_id,
								Model model) {
    	
    	Comments updateComment = commentsService.findComment(comment_id);
    	Long board_id = updateComment.getBoard_id();

    	if(updateComment == null || !updateComment.getMember_mail().equals(loginMember.getMember_mail())) {
    		
    		log.info("댓글 수정 권한 없음");
    		return "redirect:/club/chat?board_id=" + board_id;
    	}
    	
    	return "redirect:/club/chat?board_id=" + board_id + "&comment_id=" + updateComment.getComment_id();
    }
    
    // 댓글 수정 저장
    @PostMapping("updateComment")
    public String updateComment(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
    							@RequestParam Long comment_id,
    							@Validated @ModelAttribute("updateComment") CommentsUpdate commentsUpdate,
    							BindingResult result) {
        
        log.info("commentsUpdate: {}", commentsUpdate);
        
        Comments comments = commentsService.findComment(comment_id);
        
        Long board_id = comments.getBoard_id();
        
        if (comments == null || !comments.getMember_mail().equals(loginMember.getMember_mail())) {
        	log.info("수정 권한 없음");
        	return "redirect:/club/chat?board_id=" + board_id;
        }
        
        comments.setComment_contents(commentsUpdate.getComment_contents());
        
        commentsService.updateComments(comments);
        
        return "redirect:/club/chat?board_id=" + board_id;
    }
    
    
    // 댓글 삭제
    @GetMapping("deleteComment")
    public String deleteComment(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
    							@RequestParam Long comment_id) {
    	
        // board_id 에 해당하는 게시글을 가져온다.
        Comments comments = commentsService.findComment(comment_id);
        
        Long board_id = comments.getBoard_id();
        
        if (comments == null || !comments.getMember_mail().equals(loginMember.getMember_mail())) {
        	log.info("삭제 권한 없음");
        	return "redirect:/club/chat?board_id=" + board_id;
        }
        
        commentsService.removeComment(comment_id);
        
        return "redirect:/club/chat?board_id=" + board_id;
    }
    
    // 게시글 수정 페이지 이동
    @GetMapping("update")
    public String updateForm(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
    						@RequestParam Long board_id,
            				Model model) {
    	
    	Board board = boardService.findBoard(board_id);
    	// board_id에 해당하는 게시글이 없거나
    	// 게시글의 작성자가 로그인한 사용자의 아이디와 다르면 수정하지 않고 리스트로 리다이렉트 시킨다.
        if (board == null || !board.getMember_mail().equals(loginMember.getMember_mail())) {
            log.info("수정 권한 없음");
            return "redirect:/club/list";
        }
        
        // model 에 board 객체를 저장한다.
    	model.addAttribute("club", Board.toBoardUpdateForm(board));
    	
    	//첨부파일 찾기
    	BoardAttachedFile attachedFile = boardService.findFileByBoardId(board_id);
    	model.addAttribute("file", attachedFile);
    	
    	return "club/update";
    }
    
    @PostMapping("update")
    public String update(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
    					@RequestParam Long board_id,
    					@Validated @ModelAttribute("board") BoardUpdateForm updateBoard,
    					BindingResult result,
    					@RequestParam(required = false) MultipartFile file) {
    	
    	// validation 에 에러가 있으면 board/update.html 페이지로 돌아간다.
        if (result.hasErrors()) {
            return "board/update";
        }
        
        Board board = boardService.findBoard(board_id);
    	
        // Board 객체가 없거나 작성자가 로그인한 사용자의 아이디와 다르면 수정하지 않고 리스트로 리다이렉트 시킨다.
        if (board == null || !board.getMember_mail().equals(loginMember.getMember_mail())) {
            log.info("수정 권한 없음");
            return "redirect:/club/list";
        }
        
        board.setBoard_title(updateBoard.getBoard_title());
        board.setBoard_contents(updateBoard.getBoard_contents());
        //파일
        boardService.updateBoard(board, updateBoard.isFileRemoved(), file);
        
    	return "redirect:/club/list";
    }
    
    // 게시글 삭제
    @GetMapping("delete")
    public String delete(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
    					@RequestParam Long board_id) {
    	
        // board_id 에 해당하는 게시글을 가져온다.
        Board board = boardService.findBoard(board_id);
        
        // 게시글이 존재하지 않거나 작성자와 로그인 사용자의 아이디가 다르면 리스트로 리다이렉트 한다.
        if (board == null || !board.getMember_mail().equals(loginMember.getMember_mail())) {
            log.info("삭제 권한 없음");
            return "redirect:/club/list";
        }
    	
        // 댓글 삭제
        commentsService.removeAllComments(board_id);
        
        //게시글 삭제
        boardService.removeBoard(board_id);
    	
    	return "redirect:/club/list";
    }
    
    @GetMapping("list")
    public String list(Model model) {
		
        List<Board> boards = boardService.findAllClubs();
        
        // Board 리스트를 model 에 저장한다.
        model.addAttribute("boards", boards);
        
    	return "club/list";
    }
    
    @GetMapping("write")
    public String write(Model model) {
    	
    	model.addAttribute("club", new BoardWriteForm());
    	
    	return "club/write";
    }
    
    @PostMapping("write")
    public String write(@SessionAttribute(value = "loginMember", required = false) Member loginMember, 
    						@Validated @ModelAttribute("club") BoardWriteForm boardWriteForm,
            				BindingResult result, 
        					@RequestParam(required = false) MultipartFile file) {
    	if(result.hasErrors()) {
    		return "club/write";
    	}
    	
    	Board board = boardWriteForm.toBoard(boardWriteForm);
    	board.setMember_mail(loginMember.getMember_mail());
    	board.setBoard_category(BoardCategory.CLUB);
    	
    	boardService.saveBoard(board, file);
    	
    	return "redirect:/club/list";
    }
    
    @GetMapping("chat")
    public String chat(@RequestParam Long board_id,
							@RequestParam(required = false) Long comment_id,
							Model model) {
		
		log.info("id: {}", board_id);
		
		// board_id에 해당하는 게시글 찾기
		Board board = boardService.findBoard(board_id);
		
		// board_id에 해당하는 게시글이 없으면 리스트로 리다이렉트 시킨다.
		if (board == null) {
		log.info("게시글 없음");
		return "redirect:/board/club";
		}
		
		// 댓글을 조회한다.
		List<Comments> comments = commentsService.findAllComments(board_id);
		log.info("comments: {}", comments);
		
		// 댓글이 있으면 모두 model에 담아 return
		if(!comments.isEmpty() && comments != null) {
		model.addAttribute("comments", comments);
		}
		
		// 모델에 Board 객체를 저장한다.
		model.addAttribute("board", board);
		
		// 새로운 댓글 작성을 받을 model
		model.addAttribute("newComment", new CommentsWrite());
		
		if(comment_id != null) {
			Comments updateComment = commentsService.findComment(comment_id);
			// 댓글 수정 시 받을 model
			model.addAttribute("updateComment", updateComment);
		}
    	return "club/chat";
    }

}
