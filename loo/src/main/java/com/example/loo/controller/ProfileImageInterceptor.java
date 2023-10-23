package com.example.loo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.example.loo.model.file.MemberAttachedFile;
import com.example.loo.model.member.Member;
import com.example.loo.repository.MemberMapper;

@Component
public class ProfileImageInterceptor implements HandlerInterceptor{

	@Autowired
	private MemberMapper memberMapper;
	
	@Override
	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler) throws Exception{
		Member loginMember = (Member) request.getSession().getAttribute("loginMember");
//		if(loginMember !=null) {
//			MemberAttachedFile lastFile = null;
//			List<AttachedFile> file = memberMapper.findFileByMail(loginMember.getMember_mail());
//			if(!file.isEmpty()) {
//				lastFile = file.get(file.size()-1);
//			}
//		      request.setAttribute("file", lastFile);
//			}
		MemberAttachedFile file = memberMapper.findFileByMail(loginMember.getMember_mail());
		request.setAttribute("file", file);
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, 
			HttpServletResponse response,
			Object handler,
			Exception ex) throws Exception{
		
	}
	
	
}
