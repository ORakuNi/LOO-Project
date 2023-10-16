package com.example.loo.model.member.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	String permittedPath = "file:///" + System.getProperty("user.dir") + "/src/main/resources/static/";
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations(permittedPath);
		//WebMvcConfigurer.super.addResourceHandlers(registry);
	}
}
