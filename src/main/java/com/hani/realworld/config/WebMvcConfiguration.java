package com.hani.realworld.config;

import static org.springframework.http.HttpMethod.*;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.hani.realworld.infra.jwt.LoginUserMethodArgumentResolver;
import com.hani.realworld.infra.jwt.OptionalUserMethodArgumentResolver;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

	private final LoginUserMethodArgumentResolver loginUserMethodArgumentResolver;
	private final OptionalUserMethodArgumentResolver optionalUserMethodArgumentResolver;

	@Override
	public void configureContentNegotiation(final ContentNegotiationConfigurer configurer) {
		configurer.defaultContentType(MediaType.APPLICATION_JSON_UTF8);
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(loginUserMethodArgumentResolver);
		resolvers.add(optionalUserMethodArgumentResolver);
	}

	@Override
	public void addCorsMappings(final CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins("*")
			.allowedMethods(Stream.of(GET, POST, PUT, PATCH, DELETE, OPTIONS)
				.map(Enum::name)
				.toArray(String[]::new))
			.allowedHeaders("Content-Type", "X-Requested-With", "Authorization", "Cache-Control")
			.maxAge(3600L);
	}

}
