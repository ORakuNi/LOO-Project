package com.example.loo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.loo.model.member.Member;
import com.example.loo.model.member.MemberLogin;
import com.example.loo.model.member.MemberSignUp;
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
      if(!memberSignUp.getCompany_mail().contains("@")) {
         result.reject("emailError", "이메일 형식이 맞지 않습니다.");
         return "users/signup";
      }
      
      //회원가입 중복 확인
      Member checkToEmail = memberMapper.findMember(memberSignUp.getCompany_mail());
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
   public String login(@Validated @ModelAttribute MemberLogin memberLogin, BindingResult result,
                  HttpServletResponse response,
                  HttpServletRequest request) {
      
      log.info("MemberLogin: {}", memberLogin);
      if(result.hasErrors()) {
         return "users/login";
      }
      
   //로그인 검증
   Member findMember = memberMapper.findMember(memberLogin.getCompany_mail());
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
   public String logout(HttpServletResponse response, HttpServletRequest request) {
      HttpSession session = request.getSession();
      //세션 초기화
      session.invalidate();
      return "redirect:/";
   }
}