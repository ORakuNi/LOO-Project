package com.example.loo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.loo.model.member.Member;
import com.example.loo.repository.MemberMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("member")
public class MemberController {
	
	@Autowired
	private MemberMapper memberMapper;
	
	@GetMapping("join")
	public String joinForm(Model model) {
		model.addAttribute("member", new Member());
		return "member/join";
	}
	
	@PostMapping("join")
	public String join() {
		return "redirect:/";
	}
}
