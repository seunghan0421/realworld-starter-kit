package com.hani.realworld.profile.adapter.in.web;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.ParameterizedTest.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hani.realworld.profile.adapter.in.web.dto.RegisterUserRequest;
import com.hani.realworld.profile.application.port.in.RegisterUserUseCase;

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
		// json으로 변경하는 거 좋은 방법 찾아서 적용해보기
		// ObjectNode objectNode = new ObjectMapper().createObjectNode();
		// objectNode.

		// when

		// then
	}
}
