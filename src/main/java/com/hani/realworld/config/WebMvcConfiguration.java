package com.hani.realworld.config;

import static org.springframework.http.HttpMethod.*;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.hani.realworld.infra.jwt.TokenArgumentResolver;

@Configuration
public class WebMvcConfiguration  implements WebMvcConfigurer {

	@Bean
	public HandlerMethodArgumentResolver tokenArgumentResolver() {
		return new TokenArgumentResolver();
	}

	@Override
	public void configureContentNegotiation(final ContentNegotiationConfigurer configurer) {
		configurer.defaultContentType(MediaType.APPLICATION_JSON_UTF8);
	}

	@Override
	public void addCorsMappings(final CorsRegistry registry) {
		registry.addMapping("/api/**")
			.allowedOrigins("*")
			.allowedMethods(Stream.of(GET, POST, PUT, DELETE)
				.map(Enum::name)
				.toArray(String[]::new));
	}

	@Override
	public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(tokenArgumentResolver());
	}
}
