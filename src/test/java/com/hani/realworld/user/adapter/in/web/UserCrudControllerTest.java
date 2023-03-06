package com.hani.realworld.user.adapter.in.web;

import static org.junit.jupiter.params.ParameterizedTest.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.hani.realworld.user.application.port.in.RegisterUserUseCase;

@Disabled
@WebMvcTest(controllers = UserCrudController.class)
class UserCrudControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	RegisterUserUseCase registerUserUseCase;

	private static final String DISPLAY_NAME = DISPLAY_NAME_PLACEHOLDER + ARGUMENTS_PLACEHOLDER;

	// TODO 직접적으로 PARAMETER들이 같은지 비교하지 않을 것이기 때문에 ParameterizedTest 할 필요 없을것 같긴한데 추후 변경
	@ParameterizedTest(name = DISPLAY_NAME)
	@CsvSource({
		"하니","hani@naver.com","haniPasswrod",
		"허니","honey@google.com","honeyPassword"
	})
	void registerUser_Succeeds(String username, String email, String password) {
		// given


		// when

		// then
	}
}
