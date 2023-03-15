package com.hani.realworld.user.adapter.in.web;

import static com.hani.realworld.common.fixture.ProfileFixture.*;
import static com.hani.realworld.common.fixture.UserFixture.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;

import com.hani.realworld.common.descriptor.ProfileFieldDescriptor;
import com.hani.realworld.common.restdocs.ControllerTest;
import com.hani.realworld.user.application.port.in.GetProfileQuery;
import com.hani.realworld.user.application.port.in.result.ProfileResult;

@WebMvcTest(ProfileController.class)
class ProfileControllerTest extends ControllerTest {

	@MockBean
	private GetProfileQuery getProfileQuery;

	@Test
	void getProfile_Succeeds() throws Exception {
		ProfileResult response = ProfileResult.of(PROFILE1, true);

		Optional<Long> userId = Optional.ofNullable(USER1.getId().getValue());
		given(getProfileQuery.getProfile(eq(USER2.getUsername()), eq(userId))).willReturn(response);

		mockMvc.perform(
				RestDocumentationRequestBuilders.get("/api/profiles/{username}", USER2.getUsername())
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
						parameterWithName("username").description("이름")
					),
					responseFields(
						fieldWithPath("profile").type(JsonFieldType.OBJECT).description("프로필 정보")
					).andWithPrefix("profile.", ProfileFieldDescriptor.profile)
				)
			);

		then(getProfileQuery).should()
			.getProfile(eq(USER2.getUsername()), eq(userId));
	}

}
