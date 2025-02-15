package com.hani.realworld.user.adapter.in.web;

import static com.hani.realworld.common.fixture.UserFixture.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;

import com.hani.realworld.ControllerTest;
import com.hani.realworld.common.descriptor.UserFieldDescriptor;
import com.hani.realworld.user.application.port.in.GetUserQuery;
import com.hani.realworld.user.application.port.in.RegisterUserUseCase;
import com.hani.realworld.user.application.port.in.UpdateUserUseCase;
import com.hani.realworld.user.application.port.in.command.RegisterUserCommand;
import com.hani.realworld.user.application.port.in.command.UpdateUserCommand;
import com.hani.realworld.user.application.port.in.result.LoginUserResult;
import com.hani.realworld.user.application.port.in.result.UserResult;

@WebMvcTest(UserController.class)
class UserControllerTest extends ControllerTest {

	@MockBean
	private RegisterUserUseCase registerUserUseCase;

	@MockBean
	private UpdateUserUseCase updateUserUseCase;

	@MockBean
	private GetUserQuery getUserQuery;

	@DisplayName("유저 생성 Controller Test")
	@Test
	void registerUser_Succeeds() throws Exception {
		String request = createJson(REGISTER_USER_REQUEST);
		UserResult response = UserResult.of(REGISTER_USER);

		given(registerUserUseCase.register(any(RegisterUserCommand.class)))
			.willReturn(response);

		mockMvc.perform(
				RestDocumentationRequestBuilders.post("/api/users")
					.accept(APPLICATION_JSON_VALUE)
					.contentType(APPLICATION_JSON_VALUE)
					.content(request)
			)
			.andExpect(status().isCreated())
			.andDo(
				restDocs.document(
					requestFields(
						fieldWithPath("user.username").type(JsonFieldType.STRING).description("유저명"),
						fieldWithPath("user.email").type(JsonFieldType.STRING).description("이메일"),
						fieldWithPath("user.password").type(JsonFieldType.STRING).description("비밀번호")
					),
					responseFields(
						fieldWithPath("user").type(JsonFieldType.OBJECT).description("유저 정보")
					).andWithPrefix("user.", UserFieldDescriptor.registered_user)
				)
			);

		then(registerUserUseCase).should()
			.register(eq(new RegisterUserCommand(
				REGISTER_USER_REQUEST.getUsername(),
				REGISTER_USER_REQUEST.getEmail(),
				REGISTER_USER_REQUEST.getPassword())));
	}

	@DisplayName("유저 정보 수정 Controller Test")
	@Test
	void updateUser_Succeeds() throws Exception {
		String request = createJson(UPDATE_USER_REQUEST);
		LoginUserResult response = LoginUserResult.of(USER2, "updated login token");

		given(updateUserUseCase.updateUser(any(UpdateUserCommand.class), eq(USER1.getId().getValue())))
			.willReturn(response);

		mockMvc.perform(
				RestDocumentationRequestBuilders.put("/api/user")
					.accept(APPLICATION_JSON_VALUE)
					.contentType(MediaType.APPLICATION_JSON)
					.header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_VALUE)
					.content(request)
			)
			.andExpect(status().isOk())
			.andDo(
				restDocs.document(
					requestHeaders(
						headerWithName(AUTHORIZATION_HEADER_NAME).description("토큰")
					),
					requestFields(
						fieldWithPath("user.username").type(JsonFieldType.STRING).description("유저명"),
						fieldWithPath("user.email").type(JsonFieldType.STRING).description("이메일"),
						fieldWithPath("user.password").type(JsonFieldType.STRING).description("비밀번호"),
						fieldWithPath("user.image").type(JsonFieldType.STRING).description("이미지 url"),
						fieldWithPath("user.bio").type(JsonFieldType.STRING).description("자기 소개")
					),
					responseFields(
						fieldWithPath("user").type(JsonFieldType.OBJECT).description("유저 정보")
					).andWithPrefix("user.", UserFieldDescriptor.user)
				)
			);

		then(updateUserUseCase).should()
			.updateUser(eq(new UpdateUserCommand(
					USER2.getEmail(),
					USER2.getUsername(),
					"password2",
					USER2.getImage(),
					USER2.getBio())),
				eq(USER1.getId().getValue()));
	}

	@DisplayName("유저 정보 조회 Controller Test")
	@Test
	void getUser_Succeeds() throws Exception {
		UserResult response = UserResult.of(USER1);

		given(getUserQuery.getUser(eq(USER1.getId().getValue()))).willReturn(response);

		mockMvc.perform(
				RestDocumentationRequestBuilders.get("/api/user")
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isOk())
			.andDo(
				restDocs.document(
					responseFields(
						fieldWithPath("user").type(JsonFieldType.OBJECT).description("유저 정보")
					).andWithPrefix("user.", UserFieldDescriptor.user)
				)
			);

		then(getUserQuery).should()
			.getUser(eq(USER1.getId().getValue()));
	}
}
