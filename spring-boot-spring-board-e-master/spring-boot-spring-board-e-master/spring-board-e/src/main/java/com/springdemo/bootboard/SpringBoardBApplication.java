package com.springdemo.bootboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;

@SpringBootApplication(
	// WebMvcConfig에서 인코딩 설정을 했기 때문에 자동 인코딩 설정 처리 제거
	// WebMvcConfig에서 파일업로드 설정을 했기 때문에 자동 업로드 설정 처리 제거		
	exclude = { 
		HttpEncodingAutoConfiguration.class, 
		MultipartAutoConfiguration.class
	}
)
public class SpringBoardBApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBoardBApplication.class, args);
	}

}
