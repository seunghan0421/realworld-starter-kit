package com.hani.realworld.user.adapter.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hani.realworld.infra.jwt.LoginToken;
import com.hani.realworld.infra.jwt.LoginUser;
import com.hani.realworld.user.adapter.in.web.dto.response.ProfileResponse;
import com.hani.realworld.user.application.port.in.FollowProfileUseCase;
import com.hani.realworld.user.application.port.in.UnFollowProfileUseCase;
import com.hani.realworld.user.application.port.in.result.ProfileResult;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/profiles")
@RestController
public class FollowController {

	private final FollowProfileUseCase followProfileUseCase;
	private final UnFollowProfileUseCase unFollowProfileUseCase;

	@PostMapping("/{username}/follow")
	ResponseEntity<ProfileResponse> followProfile(
		@PathVariable("username") String username,
		@LoginUser LoginToken token) {

		ProfileResult profileResult =
			followProfileUseCase.followProfile(username, token.getId());

		return ResponseEntity.ok(ProfileResponse.of(profileResult));
	}

	@DeleteMapping("/{username}/follow")
	ResponseEntity<ProfileResponse> unFollowProfile(
		@PathVariable("username") String username,
		@LoginUser LoginToken token) {

		ProfileResult profileResult =
			unFollowProfileUseCase.unfollowProfile(username, token.getId());

		return ResponseEntity.ok(ProfileResponse.of(profileResult));
	}

}
