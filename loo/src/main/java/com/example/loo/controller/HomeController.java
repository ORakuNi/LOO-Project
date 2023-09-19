package com.example.loo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HomeController {

	@GetMapping("/")
	public String home() {
		return "redirect:/users/login";
	}
	
	@GetMapping("/main")
	public String main() {
		return "index";
	}
}
