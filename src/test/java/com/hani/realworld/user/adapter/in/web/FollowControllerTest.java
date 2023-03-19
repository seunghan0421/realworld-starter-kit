package com.hani.realworld.user.adapter.in.web;

import static com.hani.realworld.common.fixture.ProfileFixture.*;
import static com.hani.realworld.common.fixture.UserFixture.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;

import com.hani.realworld.ControllerTest;
import com.hani.realworld.common.descriptor.ProfileFieldDescriptor;
import com.hani.realworld.user.application.port.in.FollowProfileUseCase;
import com.hani.realworld.user.application.port.in.UnFollowProfileUseCase;
import com.hani.realworld.user.application.port.in.result.ProfileResult;

@WebMvcTest(FollowController.class)
class FollowControllerTest extends ControllerTest {

	@MockBean
	private FollowProfileUseCase followProfileUseCase;

	@MockBean
	UnFollowProfileUseCase unFollowProfileUseCase;

	@DisplayName("프로필 팔로우 Controller Test")
	@Test
	void followProfile_Succeeds() throws Exception {
		ProfileResult response = ProfileResult.of(PROFILE1, true);

		given(followProfileUseCase.followProfile(eq(USER1.getUsername()), eq(USER2.getId().getValue())))
			.willReturn(response);
		given(loginUserMethodArgumentResolver.resolveArgument(any(), any(), any(), any()))
			.willReturn(USER2_LOGIN_TOKEN);

		mockMvc.perform(
				RestDocumentationRequestBuilders.post("/api/profiles/{username}/follow", USER1.getUsername())
					.contentType(MediaType.APPLICATION_JSON)
					.header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
			)
			.andExpect(status().isOk())
			.andDo(
				restDocs.document(
					requestHeaders(
						headerWithName(AUTHORIZATION_HEADER_NAME).description("토큰")
					),
					pathParameters(
						parameterWithName("username").description("상대 이름")
					),
					responseFields(
						fieldWithPath("profile").type(JsonFieldType.OBJECT).description("프로필 정보")
					).andWithPrefix("profile.", ProfileFieldDescriptor.profile)
				)
			);

		then(followProfileUseCase).should()
			.followProfile(eq(USER1.getUsername()), eq(USER2.getId().getValue()));
	}

	@DisplayName("프로필 언팔로우 Controller Test")
	@Test
	void unFollowProfile_Succeeds() throws Exception {
		ProfileResult response = ProfileResult.of(PROFILE1, false);

		given(unFollowProfileUseCase.unfollowProfile(eq(USER1.getUsername()), eq(USER2.getId().getValue())))
			.willReturn(response);
		given(loginUserMethodArgumentResolver.resolveArgument(any(), any(), any(), any()))
			.willReturn(USER2_LOGIN_TOKEN);

		mockMvc.perform(
				RestDocumentationRequestBuilders.delete("/api/profiles/{username}/follow", USER1.getUsername())
					.contentType(MediaType.APPLICATION_JSON)
					.header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
			)
			.andExpect(status().isOk())
			.andDo(
				restDocs.document(
					requestHeaders(
						headerWithName(AUTHORIZATION_HEADER_NAME).description("토큰")
					),
					pathParameters(
						parameterWithName("username").description("상대 이름")
					),
					responseFields(
						fieldWithPath("profile").type(JsonFieldType.OBJECT).description("프로필 정보")
					).andWithPrefix("profile.", ProfileFieldDescriptor.profile)
				)
			);

		then(unFollowProfileUseCase).should()
			.unfollowProfile(eq(USER1.getUsername()), eq(USER2.getId().getValue()));
	}

}
