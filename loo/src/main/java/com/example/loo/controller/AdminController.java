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
	public String admin_update(@RequestParam String member_mail,
								Model model) {
		
		Member member = memberMapper.findMember(member_mail);
		
		model.addAttribute("update", member);
		
		return "admin/admin-update";
	}
	
	@PostMapping("admin-update")
	public String admin_pdate(@RequestParam String member_mail,
							@Validated @ModelAttribute("update") MemberAdminUpdate memberAdminUpdate,
							BindingResult result) {
		
		log.info("매니저가 선택한 사용자: {}", member_mail);
		log.info("사용자 정보: {}", memberAdminUpdate);
		
		if(result.hasErrors()) {
			return "admin/admin-update?member_mail=" + member_mail;
		}
		
		Member member = memberMapper.findMember(member_mail);
		if(member == null) {
			return "redirect:/";
		}
		
		member = MemberAdminUpdate.toMember(memberAdminUpdate);
		member.setMember_mail(member_mail);
		
		memberMapper.updateAdminMember(member);

		return "redirect:/admin/admin";
	}
	
	@GetMapping("admin-commute")
	public String admin_commute(@RequestParam String member_mail, 
								Model model) {
		
		List<Commute> findAllCommutes = commuteMapper.findAllCommutes(member_mail);
		log.info("findAllCommutes : {}", findAllCommutes);
		model.addAttribute("commutes", findAllCommutes);
		
		return "admin/admin-commute";
	}
	
}
