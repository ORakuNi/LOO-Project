package com.example.loo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.loo.model.member.Member;
import com.example.loo.model.restaurant.Restaurant;
import com.example.loo.repository.MatgipMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("api")
@Controller
@Slf4j
@RequiredArgsConstructor
public class RestaurantController {
	
	@Autowired
	private final MatgipMapper matgipMapper;
	
	@GetMapping("restaurant")
	public String restaurant() {
		
		return "api/restaurant";
	}
	
	
	@PostMapping("restaurant")
	public String myRestaurant( @SessionAttribute("loginMember") Member loginMember,
								@ModelAttribute("data") Restaurant restaurant) {
		log.info("찜 : {} " , restaurant);
		
		restaurant.setMember_mail(loginMember.getMember_mail());
		matgipMapper.saveMatgip(restaurant);
		
		return "api/restaurant";
	}
	
}
