package com.example.loo.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.loo.model.matgip.Matgip;
import com.example.loo.model.member.Member;
import com.example.loo.repository.MatgipMapper;
import com.example.loo.service.MatgipService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("api")
@Controller
@Slf4j
@RequiredArgsConstructor
public class MatgipController {
	
	@Autowired
	private final MatgipService matgipService;
	
	@GetMapping("matgip")
	public String restaurant(@SessionAttribute(name = "loginMember", required = false) Member loginMember,
							 @SessionAttribute(name = "like" ,required =  false)
							 	@ModelAttribute("data") Matgip restaurant, 
							 Model model) {
		
		if(restaurant.getMatgip_title() != null) {
			Matgip findMatgip = 
					matgipService.findMatgip(restaurant.getMatgip_title(),loginMember.getMember_mail());
			log.info("등록된 맛집 : {}", findMatgip);
			model.addAttribute("like", restaurant);
		}
		
		return "api/matgip";
	}
	
	
	@PostMapping("matgip")
	public String myRestaurant( @SessionAttribute(name = "loginMember", required = false) Member loginMember,
								@SessionAttribute(name = "like" ,required =  false)
								@ModelAttribute("data") Matgip restaurant, HttpServletRequest request) {
		log.info("찜 : {} " , restaurant);
		
		restaurant.setMember_mail(loginMember.getMember_mail());
		HttpSession session = request.getSession();
		session.setAttribute("like", restaurant.getMatgip_title());

		matgipService.saveMatgip(restaurant);			
		
		return "api/matgip";
	}
	
}