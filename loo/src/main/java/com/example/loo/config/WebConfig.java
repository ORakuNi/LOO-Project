package com.example.loo.config;

<<<<<<< HEAD
=======
import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
>>>>>>> 986e1e62bddfb799ecc4aa537bd207087168a9f1
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import com.example.loo.interceptor.AdminCheckInterceptor;
import com.example.loo.interceptor.LoginCheckInterceptor;


@Configuration
public class WebConfig implements WebMvcConfigurer {

	private String[] excludePaths = {"/", "/head", "/dropdown", "/sidebar", 
					"/users/login", "/users/signup", "/users/logout", "/users/js/app.js",
					"/*.css", "/*.fonts", "/*.js", "/*.img", "/*.ico", "/error",
					"/css/**", "/fonts/**", "/img/**", "/js/**", "/src/**"};
	
	
//	@Bean
//	public FilterRegistrationBean<Filter> loginCheckFilter() {
//		
//		FilterRegistrationBean<Filter> filterRegistrationBean = 
//				new FilterRegistrationBean<>();
//		
//		// 등록할 필터를 지정
//		filterRegistrationBean.setFilter(new LoginCheckFilter());
//		
//		// 필터의 순서적용, 숫자가 낮을 수록 먼저 실행
//		filterRegistrationBean.setOrder(1);
//		
//		// 필터를 적용할 URL 패턴을 지정
//		filterRegistrationBean.addUrlPatterns("/*"); // 최상위경로 밑으로 들어오는 모든 경로에 필터를 적용		
//		
//		return filterRegistrationBean;	
//		
//	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		// 인터셉터 등록
		registry.addInterceptor(new LoginCheckInterceptor())
		.order(1) // 호출 순서
		.addPathPatterns("/**") // 인터셉터를 적용할 URL 패턴 지정
		.excludePathPatterns(excludePaths); // 인터셉터에서 제외할 패턴을 지정
	
		
		registry.addInterceptor(new AdminCheckInterceptor())
		.order(2) // 호출 순서
		.addPathPatterns("/admin/admin"); // 인터셉터를 적용할 URL 패턴 지정
	}
	

	
	
}
