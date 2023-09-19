package com.example.loo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MemberController {

	@GetMapping("/users/signup")
	public String signUp() {
		return "users/signup";
	}
	
	@GetMapping("/users/login")
	public String login() {
		return "users/login";
		
	}
	
}
