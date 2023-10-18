package com.example.loo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.loo.repository.BoardMapper;
import com.example.loo.repository.CommentsMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("admin")
@RequiredArgsConstructor
@Controller
@Slf4j
public class AdminController {

	@GetMapping("admin")
	public String admin() {
		
		log.info("admin 페이지");
		
		return "admin/admin";
	}
	
}
