package com.example.loo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.loo.model.member.Member;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("users")
@Controller
@Slf4j
public class MemberController {

	@GetMapping("signup")
	public String signUp(Model model) {
		model.addAttribute("member", new Member());
		return "users/signup";
	}
	
	@PostMapping("signup")
	public String signup() {
		return "redirect:/";
	}
	
	@GetMapping("login")
	public String login() {
		return "users/login";
		
	}
	
}
