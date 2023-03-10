package com.hani.realworld.common.restdocs;

import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hani.realworld.config.SecurityConfiguration;

@Disabled
@WebMvcTest({
	CommonDocController.class,
})
@Import(SecurityConfiguration.class)
public class ControllerTest {

	@Autowired
	protected ObjectMapper objectMapper;

	@Autowired
	protected MockMvc mockMvc;

	protected String createJson(Object dto) throws JsonProcessingException {
		return objectMapper.writeValueAsString(dto);
	}

}
