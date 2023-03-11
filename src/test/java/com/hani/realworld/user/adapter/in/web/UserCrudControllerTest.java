package com.hani.realworld.user.adapter.in.web;

import static com.hani.realworld.common.fixture.UserFixture.*;
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
import com.hani.realworld.user.application.port.in.GetUserQuery;
import com.hani.realworld.user.application.port.in.RegisterUserUseCase;
import com.hani.realworld.user.application.port.in.UpdateUserUseCase;
import com.hani.realworld.user.application.port.in.command.RegisterUserCommand;
import com.hani.realworld.user.application.port.in.result.UserResult;

@WebMvcTest(UserCrudController.class)
class UserCrudControllerTest extends ControllerTest {

	@MockBean
	private RegisterUserUseCase registerUserUseCase;

	@MockBean
	private UpdateUserUseCase updateUserUseCase;

	@MockBean
	private GetUserQuery getUserQuery;

	@Test
	void registerUser_Succeeds() throws Exception {
		String request = createJson(REGISTER_USER_REQUEST);
		UserResult response = UserResult.of(REGISTER_USER);

		given(registerUserUseCase.register(any(RegisterUserCommand.class)))
			.willReturn(response);

		mockMvc.perform(
				RestDocumentationRequestBuilders.post("/api/users")
					.contentType(MediaType.APPLICATION_JSON)
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

		// then(registerUserUseCase).should()
		// 	.register(eq(new RegisterUserCommand(
		// 		REGISTER_USER_REQUEST.getUsername(),
		// 		REGISTER_USER_REQUEST.getEmail(),
		// 		REGISTER_USER_REQUEST.getPassword())));
	}
}
