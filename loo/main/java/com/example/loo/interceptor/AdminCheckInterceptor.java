package com.example.loo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import com.example.loo.model.member.Member;
import com.example.loo.model.member.Position;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class AdminCheckInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		// false -> 세션 정보가 없으면 null값 되게 함
		String requestURI = request.getRequestURI();
		
		Member member = (Member)session.getAttribute("loginMember");	
		
		log.info("직급: {}", member.getPosition_id());
		
		if(member.getPosition_id().equals("manager")) {
			
			log.info("관리자의 요청");
			return true;
		}
		log.info("관리자가 아닌 사용자의 요청 " + requestURI);
		
		response.sendRedirect("/");
		return false;
	}

}
