package com.example.loo.config;

import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.loo.interceptor.AdminCheckInterceptor;
import com.example.loo.interceptor.LoginCheckInterceptor;


@Configuration
public class WebConfig implements WebMvcConfigurer {

	private String[] loginCheckExcludePaths = {"/", "/head", "/dropdown", "/sidebar", 
					"/users/login", "/users/signup", "/users/logout", "/users/js/app.js",
					"/*.css", "/*.fonts", "/**/*.js", "/*.img", "/*.ico", "/error",
					"/css/**", "/fonts/**", "/img/**", "/js/**", "/src/**"};
	
	private String[]  adminCheckAddPath = {"/admin/admin", "/admin/admin-update"
	};
	
	String permittedPath = "file:///" + System.getProperty("user.dir") + "/src/main/resources/static/";
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		// 인터셉터 등록
		registry.addInterceptor(new LoginCheckInterceptor())
		.order(1) // 호출 순서
		.addPathPatterns("/**") // 인터셉터를 적용할 URL 패턴 지정
		.excludePathPatterns(loginCheckExcludePaths); // 인터셉터에서 제외할 패턴을 지정
	
		
		registry.addInterceptor(new AdminCheckInterceptor())
		.order(2) // 호출 순서
		.addPathPatterns(adminCheckAddPath); // 인터셉터를 적용할 URL 패턴 지정
	}
	

	@Override
	   public void addResourceHandlers(ResourceHandlerRegistry registry) {
	      registry.addResourceHandler("/**").addResourceLocations(permittedPath);
	      //WebMvcConfigurer.super.addResourceHandlers(registry);
	   }

	
}
