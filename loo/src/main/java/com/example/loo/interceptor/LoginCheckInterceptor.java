package com.example.loo.interceptor;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession(false);
		// false -> 세션 정보가 없으면 null값 되게 함
		String requestURI = request.getRequestURI();
		
		if(session == null || session.getAttribute("loginMember") == null) {
			
			log.info("로그인을 하지 않은 사용자의 요청 " + requestURI);
			
			// 파라미터 받은 걸 담아 줌 (여러개 일 수 있으므로 getParameterNames)
			Enumeration<String> parameterNames = request.getParameterNames();
			StringBuffer stringBuffer = new StringBuffer();
			
			// parameterNames 값이 있으면 반복문 돌아라 (없으면 안 돔)
			while(parameterNames.hasMoreElements()) {
				String parameterName = parameterNames.nextElement();
				stringBuffer.append(parameterName + "=" + request.getParameter(parameterName) + "&");
				// stringBuffer에 가져온 값의 이름과 실제 값을 주소에 값이름=값&값이름2=값2 으로 붙이기 위해
			}
			
			response.sendRedirect("/users/login?redirectURL=" 
								+ requestURI + "?" + stringBuffer.toString());
			
			log.info("Redirect정보: " + requestURI + "?" + stringBuffer.toString());
			return false;
		}		
		return true;
	}
}
