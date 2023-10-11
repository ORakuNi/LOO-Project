package com.example.loo.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.util.PatternMatchUtils;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginCheckFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpReponse = (HttpServletResponse)response;
		
		try {
			log.info("LoginCheckFilter 실행");
			String requestURI = httpRequest.getRequestURI();		
			
			if(isLoingCheckPath(requestURI)) {
				
				HttpSession session = httpRequest.getSession(false); 
				// false -> 세션 정보가 없으면 null값 되게 함

				if(session == null || session.getAttribute("loginMember") == null) {
				// 로그인 하지 않은 상태
				log.info("로그인 하지 않은 사용자의 요청");
				
				// 스프링 방식의 redirect를 쓸 수 없어서 사용
				httpReponse.sendRedirect("/users/login");
				
				return;
				}
//				log.info("로그인한 사용자: {}", session.getAttribute("loginMember"));
			}
			chain.doFilter(request, response);
			
		} catch (Exception e) {
			
		} finally {
			log.info("LoginCheckFilter 종료");
		}
		
	}

	private boolean isLoingCheckPath(String requestURI) {
		
		// 로그인 체크 X. 로그인 하지 않아도 들어갈 수 있는 경로들   -- 추가 필요
		String[] whiteList = {"/", "/users/login", "/users/signup", "/users/logout", 
				"/*.css", "/*.fonts", "/*.js", "/*.img", "/*.ico", "/error"};
	
		// 매치되는 값이 있으면 true, 매치되는 값이 없으면 false
		// !: 매치되는 값이 있으면 false, 매치되는 값이 없으면 true
		return !PatternMatchUtils.simpleMatch(whiteList, requestURI);
	}
	
}
