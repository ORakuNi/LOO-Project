package com.example.loo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.loo.model.member.Member;
import com.example.loo.service.PhonesService;

import lombok.RequiredArgsConstructor;

@RequestMapping("phones")
@RequiredArgsConstructor
@Controller
public class PhonesController {

	private final PhonesService phonesService;
	


	@GetMapping("list")
	public String phones(Model model,
			@RequestParam(value = "member_mail", required = false) String member_mail,
			@RequestParam(value = "member_name", required = false) String member_name, 
			@RequestParam(value = "phone", required = false) String phone, 
			@RequestParam(value = "department_name", required = false) String department_name) {

        // 데이터베이스에 저장된 모든 Member 객체를 리스트 형태로 받는다.
        List<Member> members = phonesService.findAllPhones();
        // Member 리스트를 model 에 저장한다.
        model.addAttribute("members", members);
        
    	// 검색기능 사용시 해당하는 member를 findMembers에 담는다.
    	List<Member> findMembers = 
    			phonesService.findMember(member_name, member_mail, phone, department_name);
    	
    	// 검색으로 찾은 findMembers를 모델에 member 객체를 저장한다.
    	if(findMembers != null) {
    		model.addAttribute("members", findMembers);
    	};
    
        
		return "phones/list";
	}
    
}
