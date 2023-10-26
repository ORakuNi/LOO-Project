package com.example.loo.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.example.loo.model.commute.Commute;
import com.example.loo.model.member.Member;
import com.example.loo.model.member.MemberAdminUpdate;
import com.example.loo.repository.CommuteMapper;
import com.example.loo.repository.MemberMapper;
import com.example.loo.repository.PhonesMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("admin")
@RequiredArgsConstructor
@Controller
@Slf4j
public class AdminController {
	
	private final PhonesMapper phonesMapper;
	private final MemberMapper memberMapper;
	private final CommuteMapper commuteMapper;

	@GetMapping("admin")
	public String admin(Model model) {
		
		log.info("admin 페이지");
		
        // 데이터베이스에 저장된 모든 Member 객체를 리스트 형태로 받는다.
        List<Member> members = phonesMapper.findAllPhones();
        // Member 리스트를 model 에 저장한다.
        model.addAttribute("members", members);
		
		return "admin/admin";
	}
	
	@GetMapping("admin-update")
	public String adminUpdate(@RequestParam String member_mail,
								Model model) {
		
		Member member = memberMapper.findMember(member_mail);
		
		model.addAttribute("update", member);
		
		return "admin/admin-update";
	}
	
	@PostMapping("admin-update")
	public String adminUpdate(@RequestParam String member_mail,
							@Validated @ModelAttribute("update") MemberAdminUpdate memberAdminUpdate,
							BindingResult result) {
		
		log.info("매니저가 선택한 사용자: {}", member_mail);
		log.info("사용자 정보: {}", memberAdminUpdate);
		
		if(result.hasErrors()) {
			return "admin/admin-update?member_mail=" + member_mail;
		}
		
		Member member = memberMapper.findMember(member_mail);
		if(member == null) {
			return "redirect:/admin/admin";
		}
		
		member = MemberAdminUpdate.toMember(memberAdminUpdate);
		member.setMember_mail(member_mail);
		
		memberMapper.updateAdminMember(member);

		return "redirect:/admin/admin";
	}
	
	@GetMapping("admin-commute")
	public String adminCommute(@RequestParam String member_mail, 
								Model model) {
		
		List<Commute> findAllCommutes = commuteMapper.findAllCommutes(member_mail);
		log.info("findAllCommutes : {}", findAllCommutes);
		model.addAttribute("commutes", findAllCommutes);
		
		return "admin/admin-commute";
	}
	

	@PostMapping("{commute_id}")
	public ResponseEntity<String> adminCommuteUpdate(@PathVariable Long commute_id,
						@RequestParam Map<String, Object> param) {
		log.info("관리자 일정 변경");
		log.info("commute_id: {}", commute_id);
		log.info("param: {}", param);
		
		Commute commute = commuteMapper.findCommute(commute_id);
		
//		if(commute == null) {
//			
//		}
		
		// Map 타입으로 가져온 param들을 String 으로 각각 담아줌
		String attendance = (String)param.get("attendance_time");
		String leave = (String)param.get("leave_time");
		
		// 다시 String 타입으로 바꾼 param들을 LocalDateTime 으로 형변환 
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		LocalDateTime attendance_time = LocalDateTime.parse(attendance, formatter);
		LocalDateTime leave_time = LocalDateTime.parse(leave, formatter);
		
		
		commute.setAttendance_time(attendance_time);
		commute.setLeave_time(leave_time);
		
		commuteMapper.updateAdminCommute(commute);
		
		return ResponseEntity.ok("업데이트 성공");
	}
	
	
	
	
//	@GetMapping("admin-commute-update")
//	public String adminCommuteUpdate(@RequestParam Long commute_id,
//						@Validated @ModelAttribute("commutes") CommuteAdminUpdate commuteAdminUpdate) {
//		log.info("관리자 일정 변경");
//		log.info("commute_id: {}", commute_id);
//		
//		Commute commute = commuteMapper.findCommute(commute_id);
//		log.info("commute: {}", commute);
//		if(commute == null) {
//			return "redirect:/admin/admin";
//		}
//		
//		commute.setAttendance_time(commuteAdminUpdate.getAttendance_time());
//		commute.setLeave_time(commuteAdminUpdate.getLeave_time());
//		
//		log.info("commuteAdminUpdate: {}", commuteAdminUpdate);
//		log.info("commute: {}", commute);
//		commuteMapper.updateAdminCommute(commute);
//		
//		
//		
//		return "redirect:/admin/admin";
//	}
	
}
