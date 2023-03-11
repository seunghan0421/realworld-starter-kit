package com.hani.realworld.common.restdocs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hani.realworld.config.SecurityConfiguration;
import com.hani.realworld.config.WebMvcConfiguration;
import com.hani.realworld.infra.jwt.JwtAuthenticationFilter;
import com.hani.realworld.infra.jwt.JwtProperties;
import com.hani.realworld.infra.jwt.JwtProvider;
import com.hani.realworld.infra.jwt.LoginUserMethodArgumentResolver;
import com.hani.realworld.user.adapter.in.web.UserCrudController;

@Disabled
// @WebMvcTest(controllers = {
// 	UserCrudController.class,
// 	CommonDocController.class
// },
// 	excludeFilters = {
// 		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfiguration.class),
// 		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class),
// 		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = LoginUserMethodArgumentResolver.class),
// 		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebMvcConfiguration.class)
// 	}
// )
@Import(RestDocsConfig.class)
@ExtendWith(RestDocumentationExtension.class)
public class ControllerTest {

	@MockBean
	protected JwtProvider jwtProvider;

	@MockBean
	protected LoginUserMethodArgumentResolver loginUserMethodArgumentResolver;

	protected ObjectMapper objectMapper;

	protected MockMvc mockMvc;

	protected RestDocumentationResultHandler restDocs;

	@BeforeEach
	void setUp(final WebApplicationContext context,
		final RestDocumentationContextProvider provider) {
		this.restDocs = context
			.getBean(RestDocumentationResultHandler.class);

		this.objectMapper = context.getBean("mappingJackson2HttpMessageConverter",
			MappingJackson2HttpMessageConverter.class).getObjectMapper();

		this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
			.addFilters(new CharacterEncodingFilter("UTF-8", true))
			.addFilters(context
				.getBean("jwtAuthenticationFilter", JwtAuthenticationFilter.class))
			.apply(MockMvcRestDocumentation.documentationConfiguration(provider))  // rest docs 설정 주입
			.alwaysDo(MockMvcResultHandlers.print())
			.alwaysDo(restDocs)
			.addFilters(new CharacterEncodingFilter("UTF-8", true)) // 한글 깨짐 방지
			.build();
	}

	protected String createJson(Object dto) throws JsonProcessingException {
		return objectMapper.writeValueAsString(dto);
	}

}
