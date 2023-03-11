package com.hani.realworld.user.adapter.in.web;

import static com.hani.realworld.common.fixture.UserFixture.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;

import com.hani.realworld.common.descriptor.UserFieldDescriptor;
import com.hani.realworld.common.restdocs.ControllerTest;
import com.hani.realworld.user.application.port.in.LoginUserUseCase;
import com.hani.realworld.user.application.port.in.command.LoginUserCommand;
import com.hani.realworld.user.application.port.in.result.LoginUserResult;

@WebMvcTest(LoginUserController.class)
class LoginUserControllerTest extends ControllerTest {

	@MockBean
	private LoginUserUseCase loginUserUseCase;

	@Test
	void loginUser_Succeeds() throws Exception {
		String request = createJson(LOGIN_USER_REQUEST);
		LoginUserResult response = LoginUserResult.of(USER1, "user1 jwt token");

		given(loginUserUseCase.login(any(LoginUserCommand.class)))
			.willReturn(response);

		mockMvc.perform(
				RestDocumentationRequestBuilders.post("/api/users/login")
					.contentType(MediaType.APPLICATION_JSON)
					.content(request)
			)
			.andExpect(status().isOk())
			.andDo(
				restDocs.document(
					requestFields(
						fieldWithPath("user.email").type(JsonFieldType.STRING).description("이메일"),
						fieldWithPath("user.password").type(JsonFieldType.STRING).description("비밀번호")
					),
					responseFields(
						fieldWithPath("user").type(JsonFieldType.OBJECT).description("유저 정보")
					).andWithPrefix("user.", UserFieldDescriptor.user)
				)
			);

		then(loginUserUseCase).should()
			.login(eq(new LoginUserCommand(
				LOGIN_USER_REQUEST.getEmail(),
				LOGIN_USER_REQUEST.getPassword())));
	}

}
