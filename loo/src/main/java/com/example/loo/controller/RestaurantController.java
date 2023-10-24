package com.example.loo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("api")
@Controller
@Slf4j
public class RestaurantController {

	@GetMapping("restaurant")
	public String restaurant() {
		
		return "api/restaurant";
	}
	
}
