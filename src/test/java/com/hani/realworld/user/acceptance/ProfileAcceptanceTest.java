package com.hani.realworld.user.acceptance;

import static com.hani.realworld.common.fixture.UserFixture.*;
import static com.hani.realworld.user.adapter.in.web.dto.response.ProfileResponse.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.DynamicTest.*;
import static org.springframework.test.annotation.DirtiesContext.*;

import java.util.stream.Stream;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.test.annotation.DirtiesContext;

import com.hani.realworld.AcceptanceTest;
import com.hani.realworld.user.adapter.in.web.dto.response.ProfileResponse;

@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
class ProfileAcceptanceTest extends AcceptanceTest {

	@DisplayName("프로필 기능 Acceptance Test")
	@TestFactory
	Stream<DynamicTest> manageProfile() {
		return Stream.of(
			dynamicTest("회원 프로필 조회", () -> {
				ProfileResponse response = get("/api/profiles/" + USER1.getUsername(), token,
					HttpStatus.SC_OK, ProfileResponse.class);
				ProfileInfo profile = response.getProfile();
				assertThat(profile.getUsername()).isEqualTo(USER1.getUsername());
			}),
			dynamicTest("팔로우", () -> {
				register(USER2, "password2");
				ProfileResponse response = post("/api/profiles/" + USER2.getUsername() + "/follow",
					"", token, HttpStatus.SC_OK, ProfileResponse.class);
				ProfileInfo profile = response.getProfile();
				assertThat(profile.getUsername()).isEqualTo(USER2.getUsername());
				assertThat(profile.isFollowing()).isTrue();
			}),
			dynamicTest("언팔로우", () -> {
				ProfileResponse response = delete(
					"/api/profiles/" + USER2.getUsername() + "/follow",
					token, HttpStatus.SC_OK, ProfileResponse.class);
				ProfileInfo profile = response.getProfile();
				assertThat(profile.getUsername()).isEqualTo(USER2.getUsername());
				assertThat(profile.isFollowing()).isFalse();
			})
		);
	}
}
