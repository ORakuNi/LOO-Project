package com.example.loo.controller;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

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

@RequestMapping("board")
@RequiredArgsConstructor
@Controller
@Slf4j
public class BoardController {
	
	private final BoardService boardService;
	private final CommentsService commentsService;
	@Value("${file.upload.path}")
	private String uploadPath;
	
	@GetMapping("list")
	public String list(@RequestParam BoardCategory board_category,
						Model model) {
		
        // 데이터베이스에 저장된 모든 Board 객체를 리스트 형태로 받는다.
        List<Board> boards = boardService.findAllBoards(board_category);
        
        // Board 리스트를 model 에 저장한다.
        model.addAttribute("boards", boards);
        
        // 카테고리 정보를 전달할 때 사용
        model.addAttribute("board_category", board_category);
		
		return "board/list";
	}
	
    // 글쓰기 페이지 이동
    @GetMapping("write")
    public String writeForm(Model model, @RequestParam BoardCategory board_category) {
    	
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
                        BindingResult result, 
                        @RequestParam(required = false) MultipartFile file,
                        RedirectAttributes redirect) {

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
        boardService.saveBoard(board, file);
        
        log.info("filesize:{}", file.getSize());
                
        //다시 redirect 원래 페이지로 돌아오게 할 때?
        redirect.addAttribute("board_category", board.getBoard_category());
        
        // board/list 로 리다이렉트한다.
        return "redirect:/board/list";
    }
    
    @GetMapping("read")
    public String read(@RequestParam Long board_id,
    					@RequestParam(required = false) Long comment_id,
    					Model model) {
    	
        log.info("id: {}", board_id);
    	
        // 조회수 1 증가
        boardService.readBoard(board_id); 
    	
    	//첨부파일
    	BoardAttachedFile attachedFile = boardService.findFileByBoardId(board_id);
    	log.info("첨부파일{}", attachedFile);
    	
    	model.addAttribute("file", attachedFile);
    	
    	// board_id에 해당하는 게시글 찾기
    	Board board = boardService.findBoard(board_id);
    	
    	// board_id에 해당하는 게시글이 없으면 리스트로 리다이렉트 시킨다.
        if (board == null) {
            log.info("게시글 없음");
            return "redirect:/board/list";
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
        model.addAttribute("board_category", board.getBoard_category());
        
        // 새로운 댓글 작성을 받을 model
        model.addAttribute("newComment", new CommentsWrite());
        
        if(comment_id != null) {
        	Comments updateComment = commentsService.findComment(comment_id);
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
    							@RequestParam BoardCategory board_category,
    							BindingResult results) {
    	
    	if(results.hasErrors()) {
    		return "redirect:/board/read?board_id=" + board_id;
    	}
    	
    	Comments comments = commentsWrite.toComments(commentsWrite);
    	comments.setBoard_id(board_id);
    	comments.setMember_mail(loginMember.getMember_mail());
    	commentsService.saveComments(comments);
    	
    	return "redirect:/board/read?board_id=" + board_id;
    }
    
    // 댓글 수정
    @GetMapping("updateComment")
    public String updateComment(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
								@RequestParam Long comment_id,
								@RequestParam BoardCategory board_category,
								Model model) {
    	
    	Comments updateComment = commentsService.findComment(comment_id);
    	Long board_id = updateComment.getBoard_id();

    	if(updateComment == null || !updateComment.getMember_mail().equals(loginMember.getMember_mail())) {
    		log.info("댓글 수정 권한 없음");
    		return "redirect:/board/read?board_id=" + board_id;
    	}
    	
    	return "redirect:/board/read?board_id=" + board_id + "&comment_id=" + updateComment.getComment_id();
    	
    }
    
    // 댓글 수정 저장
    @PostMapping("updateComment")
    public String updateComment(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
    							@RequestParam Long comment_id,
    							@RequestParam BoardCategory board_category,
    							@Validated @ModelAttribute("updateComment") CommentsUpdate commentsUpdate,
    							BindingResult result) {
        
        log.info("commentsUpdate: {}", commentsUpdate);
        
        Comments comments = commentsService.findComment(comment_id);
        
        Long board_id = comments.getBoard_id();
        
        // Board 객체가 없거나 작성자가 로그인한 사용자의 아이디와 다르면 수정하지 않고 리스트로 리다이렉트 시킨다.
        if (comments == null || !comments.getMember_mail().equals(loginMember.getMember_mail())) {
            log.info("수정 권한 없음");
            return "redirect:/board/read";
        }
        
        // 제목과 내용 수정
        comments.setComment_contents(commentsUpdate.getComment_contents());
        
        // 수정한 Board 를 데이터베이스에 update 한다.
        commentsService.updateComments(comments);
        
    	return "redirect:/board/read?board_id=" + board_id;
    }
    
    
    // 댓글 삭제
    @GetMapping("deleteComment")
    public String deleteComment(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
    							@RequestParam BoardCategory board_category,
    							@RequestParam Long comment_id) {
    	
        // board_id 에 해당하는 게시글을 가져온다.
        Comments comments = commentsService.findComment(comment_id);
        
        Long board_id = comments.getBoard_id();

        // 게시글이 존재하지 않거나 작성자와 로그인 사용자의 아이디가 다르면 리스트로 리다이렉트 한다.
        if (comments == null || !comments.getMember_mail().equals(loginMember.getMember_mail())) {
            log.info("삭제 권한 없음");
            return "redirect:/board/read";
        }
    	
        // 댓글 삭제
        commentsService.removeComment(comment_id);
    	
    	return "redirect:/board/read?board_id=" + board_id;
    }
    
    // 게시글 수정 페이지 이동
    @GetMapping("update")
    public String updateForm(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
    						@RequestParam Long board_id,
    						@RequestParam BoardCategory board_category,
            				Model model) {
    	
    	Board board = boardService.findBoard(board_id);
    	// board_id에 해당하는 게시글이 없거나
    	// 게시글의 작성자가 로그인한 사용자의 아이디와 다르면 수정하지 않고 리스트로 리다이렉트 시킨다.
        if (board == null || !board.getMember_mail().equals(loginMember.getMember_mail())) {
            log.info("수정 권한 없음");
            return "redirect:/board/list?board_category=" + board_category;
        }
        
        // model 에 board 객체를 저장한다.
    	model.addAttribute("board", Board.toBoardUpdateForm(board));
    	
    	//첨부파일 찾기
    	BoardAttachedFile attachedFile = boardService.findFileByBoardId(board_id);
    	model.addAttribute("file", attachedFile);
    	
    	return "board/update";
    }
    
    @PostMapping("update")
    public String update(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
    					@RequestParam Long board_id,
    					@Validated @ModelAttribute("board") BoardUpdateForm updateBoard,
    					BindingResult result,
    					@RequestParam(required = false) MultipartFile file,
    					RedirectAttributes redirect) {
    	
        log.info("board: {}", updateBoard);
    	
    	// validation 에 에러가 있으면 board/update.html 페이지로 돌아간다.
        if (result.hasErrors()) {
            return "board/update";
        }
        
        Board board = boardService.findBoard(board_id);
    	
        // Board 객체가 없거나 작성자가 로그인한 사용자의 아이디와 다르면 수정하지 않고 리스트로 리다이렉트 시킨다.
        if (board == null || !board.getMember_mail().equals(loginMember.getMember_mail())) {
            log.info("수정 권한 없음");
            return "redirect:/board/list";
        }
        
        // 제목과 내용 수정
        board.setBoard_title(updateBoard.getBoard_title());
        board.setBoard_contents(updateBoard.getBoard_contents());
       
        // 수정한 Board 를 데이터베이스에 update 한다.
        boardService.updateBoard(board, updateBoard.isFileRemoved(), file);
        
        redirect.addAttribute("board_category", board.getBoard_category());
        
    	return "redirect:/board/list";
    }
    
    // 게시글 삭제
    @GetMapping("delete")
    public String delete(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
    					@RequestParam Long board_id, RedirectAttributes redirect) {
    	
        // board_id 에 해당하는 게시글을 가져온다.
        Board board = boardService.findBoard(board_id);
        
        // 게시글이 존재하지 않거나 작성자와 로그인 사용자의 아이디가 다르면 리스트로 리다이렉트 한다.
        if (board == null || !board.getMember_mail().equals(loginMember.getMember_mail())) {
            log.info("삭제 권한 없음");
            return "redirect:/board/list";
        }
    	
        // 댓글 삭제
        commentsService.removeAllComments(board_id);
        
        //게시글 삭제
        boardService.removeBoard(board_id);
    	
    	redirect.addAttribute("board_category", board.getBoard_category());
    	
    	return "redirect:/board/list";
    }
    
    @GetMapping("download/{id}")
    public ResponseEntity<Resource> download(@PathVariable Long id) throws MalformedURLException {
    	
    	//첨부파일 아이디로 첨부파일 정보를 가져온다.
    	BoardAttachedFile attachedFile = boardService.findFileByAttachedFileId(id);
    	
    	//다운로드 하려는 파일의 절대경로 값을 만든다.
    	String fullPath = uploadPath + "/" + attachedFile.getSaved_filename();
    	
    	UrlResource resource = new UrlResource("file:" + fullPath);
    	
    	//한글 파일명이 깨지지 않도록 UTF-8로 파일명을 인코딩한다.
    	String encodingFileName = UriUtils.encode(attachedFile.getOriginal_filename(), StandardCharsets.UTF_8);
    	
    	//응답헤더에 담을 Content Disposition 값을 생성한다.
    	String contentDispostion = "attachment; filename=\""+ encodingFileName + "\"";
    	
    	return ResponseEntity.ok()
    						 .header(HttpHeaders.CONTENT_DISPOSITION, contentDispostion)
    						 .body(resource);
    }
    
}