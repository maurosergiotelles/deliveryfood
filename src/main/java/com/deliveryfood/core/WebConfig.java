package com.deliveryfood.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.servlet.Filter;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")

				.allowedOrigins("*")

//		.allowedMethods("GET")
//		
//		.maxAge(10)

		;

	}

	@Bean
	Filter shallowEtagFilter() {
		return new ShallowEtagHeaderFilter();
	}

}