package com.example.loo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.loo.model.matgip.Matgip;
import com.example.loo.model.member.Member;
import com.example.loo.repository.MatgipMapper;

import lombok.RequiredArgsConstructor;

@RequestMapping("api")
@Controller
@RequiredArgsConstructor
public class MatgipController {

	private final MatgipMapper matgipMapper;

	@GetMapping("matgip")
	public String restaurant(@SessionAttribute(name = "loginMember", required = false) Member loginMember,
							@SessionAttribute(name = "like", required = false) 
							@ModelAttribute("data") Matgip restaurant,
							Model model) {

		return "api/matgip";
	}

	@PostMapping("matgip")
	public String myRestaurant(@SessionAttribute(name = "loginMember", required = false) Member loginMember,
							   @ModelAttribute("data") Matgip matgip, BindingResult result) {
		
		matgip.setMember_mail(loginMember.getMember_mail());
		
		Matgip findMatgip = matgipMapper.findMatgipTitle(matgip.getMatgip_title(), loginMember.getMember_mail());
		
		if(findMatgip == null) {
			matgipMapper.saveMatgip(matgip);			
		} else result.reject("alreadyMatgip", "이미 찜한 목록입니다");

		return "api/matgip";
	}

	@GetMapping("myMatgip")
	public String myMatgip(@SessionAttribute(name = "loginMember", required = false) Member loginMember,
			@RequestParam String member_mail, Model model) {

		List<Matgip> findMatgip = matgipMapper.findMatgip(member_mail);
		model.addAttribute("myMat", findMatgip);

		return "api/myMatgip";
	}
	
	@GetMapping("delete")
	public String deleteMatgip(@SessionAttribute(name = "loginMember", required = false) Member loginMember,
								@RequestParam String member_mail, 
								@RequestParam("matgip_title") String matgip_title) {
	
		matgipMapper.removeMatgip(matgip_title, loginMember.getMember_mail());
		
		
		return "redirect:/api/matgip";
	}
	

}