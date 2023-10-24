package com.example.loo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.loo.model.board.Board;
import com.example.loo.model.board.BoardCategory;
import com.example.loo.model.commute.Commute;
import com.example.loo.model.commute.CommuteAttendance;
import com.example.loo.model.member.Member;
import com.example.loo.repository.BoardMapper;
import com.example.loo.repository.CommuteMapper;
import com.example.loo.service.BoardService;
import com.example.loo.util.PageNavigator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.example.loo.model.board.Board;
import com.example.loo.model.board.BoardCategory;
import com.example.loo.repository.BoardMapper;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {
	
	@Autowired
	private BoardMapper boardMapper;
	
	private final CommuteMapper commuteMapper;
	private final BoardService boardService;
	private Commute findCommute;
	private final int countPerPage = 5;
	private final int pagePerGroup = 1;

	@GetMapping("/")
	public String home(@SessionAttribute(name = "status", required = false)
					@ModelAttribute CommuteAttendance commuteAttendance, 
					HttpServletRequest request,
					Model model) {
		
        //페이징
        int total = boardService.getTotal(BoardCategory.NOTICE);
        int page = 1;
        
		PageNavigator navi = new PageNavigator(countPerPage, pagePerGroup, page, total);
		
		RowBounds rowBounds = new RowBounds(0, countPerPage);
		
        // 데이터베이스에 저장된 모든 Board 객체를 리스트 형태로 받는다.
        List<Board> boards = boardMapper.findAllBoards(BoardCategory.NOTICE, rowBounds);
		
	    // Board 리스트를 model 에 저장한다.
	    model.addAttribute("boards", boards);

		HttpSession session = request.getSession();
		
		Object attribute = session.getAttribute("status");
		
		if(attribute != null) {
			Commute findCommute = commuteMapper.findCommute(((Commute)attribute).getCommute_id());
			model.addAttribute("commute", findCommute.getCommute_status());
		}
		
		// 공지 게시판
<<<<<<< HEAD
		List<Board> noticeBoardList = boardMapper.findAllBoards(BoardCategory.NOTICE, rowBounds);
=======
		List<Board> noticeBoardList = boardMapper.findAllBoards(BoardCategory.NOTICE);
>>>>>>> 986e1e62bddfb799ecc4aa537bd207087168a9f1
		model.addAttribute("noticeBoardList", noticeBoardList);
		return "index";
	}

	
	
	// 출근하기
	@PostMapping("attendance")
	public String attend(@SessionAttribute(name = "status", required = false)@ModelAttribute CommuteAttendance commuteAttendance, 
						 HttpServletRequest request,
						 @SessionAttribute(name = "loginMember", required = false) Member loginMember) {
		
		commuteAttendance.setMember_mail(loginMember.getMember_mail());
		commuteAttendance.setCommute_status("1");
		
		Commute commute = CommuteAttendance.toCommute(commuteAttendance);
		commuteMapper.insertCommute(commute);
		HttpSession session = request.getSession();
		session.setAttribute("status", commute);
		log.info("commute:{}",commute);
		return "redirect:/";
	}
	
	
	
	@PostMapping("leave")
	public String leave(@SessionAttribute(name = "status", required = false)
						@ModelAttribute CommuteAttendance commuteAttendance, HttpServletRequest request,
						@SessionAttribute(name = "loginMember", required = false) Member loginMember) {
		
		HttpSession session = request.getSession();
	
		Object attribute = session.getAttribute("status");
		session.setAttribute("status", attribute);

		// session에 있는 commute_id를 들고와서 형변환 시켜줌
//		log.info("status : {}" , ((Commute) attribute).getCommute_id());
		findCommute = commuteMapper.findCommute(((Commute) attribute).getCommute_id());
		commuteMapper.updateCommute(findCommute);
		return "redirect:/";
	}
	
	
	
	// 전체 출퇴근 기록 가져오기
	@GetMapping("list")
	public String list(@RequestParam String member_mail, Model model) {
		
		List<Commute> findAllCommutes = commuteMapper.findAllCommutes(member_mail);
		log.info("findAllCommutes : {}", findAllCommutes);
		model.addAttribute("commutes", findAllCommutes);
		
		return "commute/list";
	}
}
