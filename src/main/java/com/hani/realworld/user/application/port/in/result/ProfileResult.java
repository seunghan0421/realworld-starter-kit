package com.hani.realworld.user.application.port.in.result;

import com.hani.realworld.user.domain.Profile;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProfileResult {
	private final String username;
	private final String bio;
	private final String image;
	private final boolean following;

	public static ProfileResult of(Profile profile, boolean isFollowing) {
		return new ProfileResult(
			profile.getUser().getUsername(),
			profile.getUser().getBio(),
			profile.getUser().getImage(),
			isFollowing
		);
	}
}
