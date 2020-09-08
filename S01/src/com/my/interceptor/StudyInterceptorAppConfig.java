package com.my.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class StudyInterceptorAppConfig implements WebMvcConfigurer {

	@Autowired
	StudyInterceptor studyInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		System.out.println("addInterceptors, studyInterceptor=" + studyInterceptor);
		registry.addInterceptor(studyInterceptor);
	}
	
	
}
