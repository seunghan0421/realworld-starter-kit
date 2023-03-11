package com.hani.realworld.common.restdocs;

import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hani.realworld.config.SecurityConfiguration;
import com.hani.realworld.config.WebMvcConfiguration;
import com.hani.realworld.infra.jwt.JwtAuthenticationFilter;
import com.hani.realworld.infra.jwt.LoginUserMethodArgumentResolver;

@Disabled
@WebMvcTest(controllers = {
	CommonDocController.class
},
	excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfiguration.class),
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class),
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = LoginUserMethodArgumentResolver.class),
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebMvcConfiguration.class)
	}
)
public class ControllerTest {

	@Autowired
	protected ObjectMapper objectMapper;

	@Autowired
	protected MockMvc mockMvc;

	protected String createJson(Object dto) throws JsonProcessingException {
		return objectMapper.writeValueAsString(dto);
	}

}
