package com.example.loo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.loo.model.member.Member;
import com.example.loo.repository.BoardMapper;
import com.example.loo.repository.CommentsMapper;
import com.example.loo.repository.PhonesMapper;
import com.example.loo.service.BoardService;

import lombok.RequiredArgsConstructor;

@RequestMapping("phones")
@Controller
public class PhonesController {

	private final PhonesMapper phonesMapper;
	
	@Autowired
    public PhonesController(PhonesMapper phonesMapper) {
        this.phonesMapper = phonesMapper;
    }

	@GetMapping("list")
	public String phones(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
			@RequestParam(value = "member_mail", required = false) String member_mail,
			@RequestParam(value = "member_name", required = false) String member_name, 
			@RequestParam(value = "phone", required = false) String phone, 
			@RequestParam(value = "department_name", required = false) String department_name,
			Model model) {
		
		// 로그인 상태가 아니면 로그인 페이지로 보낸다.
        if (loginMember == null) {
            return "redirect:/users/login";
        }
		
        // 데이터베이스에 저장된 모든 Member 객체를 리스트 형태로 받는다.
        List<Member> members = phonesMapper.findAllPhones();
        // Member 리스트를 model 에 저장한다.
        model.addAttribute("members", members);
		
    	if(!(member_mail==null ||member_mail.isEmpty()) || !(member_name==null||member_name.isEmpty()) 
        		|| !(phone ==null || phone.isEmpty()) || !(department_name == null || department_name.isEmpty())) {
    	List<Member> member = phonesMapper.findMember(member_name, member_mail, phone, department_name);
    	if(member != null) {
    		// 모델에 member 객체를 저장한다.
    		model.addAttribute("members", member);
    }
    }
        
		return "phones/list";
	}
    
}
