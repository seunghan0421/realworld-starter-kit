package com.hani.realworld;

import static com.hani.realworld.common.fixture.UserFixture.*;
import static com.hani.realworld.infra.jwt.JwtAuthenticationFilter.*;
import static com.hani.realworld.infra.jwt.JwtProvider.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
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
import com.hani.realworld.common.restdocs.RestDocsConfig;
import com.hani.realworld.infra.jwt.JwtAuthenticationFilter;
import com.hani.realworld.infra.jwt.JwtProvider;
import com.hani.realworld.infra.jwt.LoginUserMethodArgumentResolver;
import com.hani.realworld.infra.jwt.OptionalUserMethodArgumentResolver;
import com.hani.realworld.user.application.port.out.LoadUserWithEmailPort;

@Disabled
@Import(RestDocsConfig.class)
@ExtendWith(RestDocumentationExtension.class)
public class ControllerTest {

	protected static final String TOKEN = "abc.def.ghi";
	protected static final String AUTHORIZATION_HEADER_NAME = AUTHORIZATION_HEADER;
	protected static final String AUTHORIZATION_HEADER_VALUE = HEADER_PREFIX + TOKEN;

	@MockBean
	protected JwtProvider jwtProvider;

	@MockBean
	protected LoginUserMethodArgumentResolver loginUserMethodArgumentResolver;

	@MockBean
	protected OptionalUserMethodArgumentResolver optionalUserMethodArgumentResolver;

	@MockBean
	protected LoadUserWithEmailPort loadUserWithEmailPort;

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

		given(loadUserWithEmailPort.loadUserWithEmail(any())).willReturn(USER1);
		given(loginUserMethodArgumentResolver.supportsParameter(any())).willReturn(true);
		given(loginUserMethodArgumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(USER1_LOGIN_TOKEN);
		given(optionalUserMethodArgumentResolver.supportsParameter(any())).willReturn(true);
		given(optionalUserMethodArgumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(USER1_LOGIN_TOKEN);
	}

	protected String createJson(Object dto) throws JsonProcessingException {
		return objectMapper.writeValueAsString(dto);
	}

}
