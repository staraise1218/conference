package com.xq.conference;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@MapperScan("com.xq.conference")
@SpringBootApplication
//@EnableCaching// 开启缓存，需要显示的指定
public class StartLYApplication extends SpringBootServletInitializer {
	static {
		try {
			Class.forName("com.xq.conference.util.UrlUtil");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(StartLYApplication.class);
	}/*
	
	@Bean 
	public MultipartConfigElement multipartConfigElement(){ 
		MultipartConfigFactory config = new MultipartConfigFactory(); 
		config.setMaxFileSize("800MB"); 
		config.setMaxRequestSize("1000MB"); 
		return config.createMultipartConfig(); 
	} */
	public static void main(String[] args) {
		SpringApplication.run(StartLYApplication.class, args);
	}
	
}