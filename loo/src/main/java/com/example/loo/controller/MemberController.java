package com.example.loo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

import com.example.loo.model.member.Member;
import com.example.loo.model.member.MemberLogin;
import com.example.loo.model.member.MemberSignUp;
import com.example.loo.model.member.MemberUpdate;
import com.example.loo.repository.MemberMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("users")
@Controller
public class MemberController {

   private MemberMapper memberMapper;
   
   @Autowired
   public void setMemberMapper(MemberMapper memberMapper) {
      this.memberMapper = memberMapper;
   }
   
   //회원가입 페이지 이동
   @GetMapping("signup")
   public String signUp(Model model) {
      model.addAttribute("member", new Member());
      return "users/signup";
   }
   
   //post 요청 처리할 때
   @PostMapping("signup")   
   public String signUp(@Validated @ModelAttribute("member")
                  MemberSignUp memberSignUp, BindingResult result, Model model) {
      
      //회원가입 처리
      log.info("users:{}", memberSignUp);
      log.info("result{}", result);
      
      if(result.hasErrors()) {
         return "users/signup";
      }
      
      
      //이메일 주소검증
      if(!memberSignUp.getMember_mail().contains("@")) {
         result.reject("emailError", "이메일 형식이 맞지 않습니다.");
         return "users/signup";
      }
      
      //회원가입 중복 확인
      Member checkToEmail = memberMapper.findMember(memberSignUp.getMember_mail());
      if (checkToEmail != null) {
         result.reject("alreadyUsedId", "이미 가입된 이메일입니다.");
         return "users/signup";
      }
      
      // 중복 아닐 때 회원 정보 저장
      memberMapper.saveMember(MemberSignUp.toMember(memberSignUp));
      return "redirect:/";
   }
   
   // 로그인 페이지로 이동
   @GetMapping("login")
   public String login(Model model) {
      model.addAttribute("login", new MemberLogin());
      return "users/login";
   }
   
   // 로그인
   @PostMapping("login")
   public String login(@SessionAttribute(name="loginMember", required = false) 
   						@Validated @ModelAttribute MemberLogin memberLogin, BindingResult result,
   						HttpServletRequest request) {
      
      log.info("MemberLogin: {}", memberLogin);
      if(result.hasErrors()) {
         return "users/login";
      }
      
   //로그인 검증
   Member findMember = memberMapper.findMember(memberLogin.getMember_mail());
   log.info("findMember:{}", findMember);
   if(findMember == null || !findMember.getPassword().equals(memberLogin.getPassword())) {
      result.reject("loginError", "아이디가 존재하지 않거나 패스워드가 다릅니다");
      return "users/login";
      }
   
   //세션을 이용한 로그인 처리
   HttpSession session = request.getSession();
   session.setAttribute("loginMember", findMember);
   
   return "redirect:/";
   }
   
   //로그아웃
   @GetMapping("logout")
   public String logout(HttpServletRequest request) {
      HttpSession session = request.getSession();
      //세션 초기화
      session.invalidate();
      return "redirect:/";
   }
   
   @GetMapping("update")
	public String update(Model model,
						@SessionAttribute(name="loginMember", required = false) Member loginMember) {
		if(loginMember == null) {
			return "redirect:/users/login";
		}
		Member member = memberMapper.findMember(loginMember.getMember_mail());
		if(member == null || !member.getMember_mail().equals(loginMember.getMember_mail())) {
			return "redirect:/";
		}
		model.addAttribute("update", member);
		return "users/update";
	}
	
	@PostMapping("update")
	public String update(@SessionAttribute(name="loginMember", required = false) Member loginMember,
						@RequestParam String company_mail,
						@ModelAttribute("member") MemberUpdate memberUpdate,
						BindingResult result) {
		if(loginMember == null) {
			return "redirect:/users/login";
		}
		log.info("company_mail: {}, member:{}", company_mail, memberUpdate);
		if(result.hasErrors()) {
			return "users/update";
		}
		Member member = memberMapper.findMember(company_mail);
		if(member == null || !member.getMember_mail().equals(loginMember.getMember_mail())) {
			return "redirect:/board/list";
		}
		member.setPassword(memberUpdate.getPassword());
		member.setPhone(memberUpdate.getPhone());
		log.info("member: {}", member);
		memberMapper.updateMember(member);
		
		return "redirect:/";
	}
}