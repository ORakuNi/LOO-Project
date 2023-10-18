package com.example.loo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.loo.model.board.Board;
import com.example.loo.model.board.BoardUpdateForm;
import com.example.loo.model.member.Member;
import com.example.loo.repository.PhonesMapper;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("phones")
@Controller
@Slf4j
public class PhonesController {

	private final PhonesMapper phonesMapper;
	
	@Autowired
    public PhonesController(PhonesMapper phonesMapper) {
        this.phonesMapper = phonesMapper;
    }

	@GetMapping("list")
	public String phones(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
			Model model) {
		
		// 로그인 상태가 아니면 로그인 페이지로 보낸다.
        if (loginMember == null) {
            return "redirect:/users/login";
        }
		
        // 데이터베이스에 저장된 모든 Member 객체를 리스트 형태로 받는다.
        List<Member> members = phonesMapper.findAllPhones();
        // Member 리스트를 model 에 저장한다.
        model.addAttribute("members", members);
		
		return "phones/list";
	}
    
    @GetMapping("read")
    public String search(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
    					@RequestParam(required = false) String member_mail,
    					@RequestParam(required = false) String member_name, 
    					@RequestParam(required = false) String phone, 
    					@RequestParam(required = false) String department_name, Model model) {
  
    	
        // 로그인 상태가 아니면 로그인 페이지로 보낸다.
        if (loginMember == null) {
            return "redirect:/users/login";
        }
    	 
        
        if((member_mail==null ||member_mail.isEmpty()) && (member_name==null||member_name.isEmpty()) 
        		&& (phone ==null || phone.isEmpty()) && department_name.isEmpty()) 
        {
        	return "redirect:/phones/list";
        } else {
        	List<Member> member = phonesMapper.findMember(member_name, member_mail, phone, department_name);
        	if(member != null) {
        		// 모델에 member 객체를 저장한다.
        		model.addAttribute("member", member);
        }
        }
    	
    	return "phones/read";
    }
    
}
