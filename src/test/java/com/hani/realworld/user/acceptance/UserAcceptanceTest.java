package com.hani.realworld.user.acceptance;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.DynamicTest.*;

import java.util.stream.Stream;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.test.annotation.DirtiesContext;

import com.hani.realworld.AcceptanceTest;
import com.hani.realworld.user.adapter.in.web.dto.request.LoginUserRequest;
import com.hani.realworld.user.adapter.in.web.dto.request.UpdateUserRequest;
import com.hani.realworld.user.adapter.in.web.dto.response.RegisterUserRequest;
import com.hani.realworld.user.adapter.in.web.dto.response.UserResponse;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class UserAcceptanceTest extends AcceptanceTest {

	@DisplayName("유저 기능 Acceptance Test")
	@TestFactory
	Stream<DynamicTest> manageUser() {
		RegisterUserRequest registerRequest = new RegisterUserRequest(
			"hani",
			"hani@naver.com",
			"password");
		LoginUserRequest loginRequest = new LoginUserRequest(
			"hani@naver.com",
			"password");
		UpdateUserRequest updateRequest = new UpdateUserRequest(
			"update@naver.com",
			"updatedHani",
			"updatedPassword",
			"https://image.jpeg",
			"hani bio");

		return Stream.of(
			dynamicTest("회원가입", () -> {
				String body = objectMapper.writeValueAsString(registerRequest);
				UserResponse response = post("/api/users", body, HttpStatus.SC_CREATED, UserResponse.class);
				assertThat(response.getEmail()).isEqualTo(registerRequest.getEmail());
				assertThat(response.getUsername()).isEqualTo(registerRequest.getUsername());
			}),
			dynamicTest("로그인", () -> {
				String body = objectMapper.writeValueAsString(loginRequest);
				UserResponse response = post("/api/users/login", body, HttpStatus.SC_OK, UserResponse.class);
				token = response.getToken();
				assertThat(token).isNotNull();
			}),
			dynamicTest("개인 정보 조회", () -> {
				UserResponse response = get("/api/user", token, HttpStatus.SC_OK, UserResponse.class);
				assertThat(response.getEmail()).isEqualTo(loginRequest.getEmail());
			}),
			dynamicTest("개인 정보 수정", () -> {
				String body = objectMapper.writeValueAsString(updateRequest);
				var response = put("/api/user", body, token, HttpStatus.SC_OK, UserResponse.class);
				assertThat(response.getEmail()).isEqualTo(updateRequest.getEmail());
				assertThat(response.getToken()).isNotEqualTo(token);
			})
		);
	}
}
