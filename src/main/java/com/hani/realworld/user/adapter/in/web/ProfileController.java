package com.hani.realworld.user.adapter.in.web;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hani.realworld.infra.jwt.LoginToken;
import com.hani.realworld.infra.jwt.OptionalUser;
import com.hani.realworld.user.adapter.in.web.dto.response.ProfileResponse;
import com.hani.realworld.user.application.port.in.GetProfileQuery;
import com.hani.realworld.user.application.port.in.result.ProfileResult;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/profiles")
@RestController
public class ProfileController {

	private final GetProfileQuery getProfileQuery;

	@GetMapping("/{username}")
	ResponseEntity<ProfileResponse> getProfile(
		@PathVariable("username") String username,
		@OptionalUser LoginToken token) {

		Optional<Long> userId = Optional.ofNullable(token).map(LoginToken::getId);

		ProfileResult profileResult = getProfileQuery.getProfile(username, userId);

		return ResponseEntity.ok(ProfileResponse.of(profileResult));
	}
}
