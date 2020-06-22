package com.springdemo.bootboard.config;

import java.nio.charset.Charset;

import javax.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig 
	implements WebMvcConfigurer { //이 설정 클래스가 spring mvc 설정 수행

	@Bean
	public Filter characterEncodingFilter() {
		//웹 요청/응답에 대한 Encoding 설정
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		filter.setForceEncoding(true);
		return filter;
	}
	
	@Bean
	public HttpMessageConverter<String> responseBodyConverter() {
		//@ResponseBody 응답에 대한 Encoding 설정
		return new StringHttpMessageConverter(Charset.forName("UTF-8"));
	}
	
	//apache commons fileupload library를 사용해서 
	//파일 업로드 처리를 수행하는 bean 등록
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setDefaultEncoding("UTF-8");
		resolver.setMaxUploadSizePerFile(1024 * 1024 * 5);
		return resolver;
	}
	
}









