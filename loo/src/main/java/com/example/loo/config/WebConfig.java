package com.example.loo.config;

import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.loo.filter.LoginCheckFilter;


@Configuration
public class WebConfig implements WebMvcConfigurer {

	private String[] excludePaths = {"/", "/users/login", "/users/signup", "/users/logout", 
			"/*.css", "/*.fonts", "/*.js", "/*.img", "/*.ico", "/error"};
	
	
	@Bean
	public FilterRegistrationBean<Filter> loginCheckFilter() {
		
		FilterRegistrationBean<Filter> filterRegistrationBean = 
				new FilterRegistrationBean<>();
		
		// 등록할 필터를 지정
		filterRegistrationBean.setFilter(new LoginCheckFilter());
		
		// 필터의 순서적용, 숫자가 낮을 수록 먼저 실행
		filterRegistrationBean.setOrder(1);
		
		// 필터를 적용할 URL 패턴을 지정
		filterRegistrationBean.addUrlPatterns("/*"); // 최상위경로 밑으로 들어오는 모든 경로에 필터를 적용
		
		
		return filterRegistrationBean;
		
		
		
	}
	
	
}
