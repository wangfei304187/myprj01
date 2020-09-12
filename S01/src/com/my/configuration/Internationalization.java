package com.my.configuration;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Component
@Configurable
public class Internationalization implements WebMvcConfigurer{

	@Bean
	public LocaleResolver localeResolver()
	{
		SessionLocaleResolver s = new SessionLocaleResolver();
		s.setDefaultLocale(Locale.US);
		return s;
	}
	
//	@Bean
//	public LocaleChangeInterceptor localeChangeInterceptor() {
//		LocaleChangeInterceptor l = new LocaleChangeInterceptor();
//		l.setParamName("lang");
//		return l;
//	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		LocaleChangeInterceptor l = new LocaleChangeInterceptor();
		l.setParamName("lang");
		System.out.println("********** addInterceptors, LocaleChangeInterceptor=" + l);
		registry.addInterceptor(l);
	}
}
