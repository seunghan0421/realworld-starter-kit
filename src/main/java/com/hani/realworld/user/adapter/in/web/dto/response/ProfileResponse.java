package com.hani.realworld.user.adapter.in.web.dto.response;

import com.hani.realworld.user.application.port.in.result.ProfileResult;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProfileResponse {

	private ProfileInfo profile;

	public static ProfileResponse of(ProfileResult profileResult) {
		return new ProfileResponse(ProfileInfo.of(profileResult));
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	public static class ProfileInfo {
		private String username;
		private String bio;
		private String image;
		private boolean following;

		public static ProfileInfo of(ProfileResult result) {
			return new ProfileInfo(
				result.getUsername(),
				result.getBio(),
				result.getImage(),
				result.isFollowing()
			);
		}
	}
}
