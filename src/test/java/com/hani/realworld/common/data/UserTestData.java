package com.hani.realworld.common.data;

import static com.hani.realworld.user.domain.User.*;

import com.hani.realworld.user.domain.Password;
import com.hani.realworld.user.domain.User;

public class UserTestData {

	public static UserBuilder defaultUser() {
		return new UserBuilder()
			.withUserId(new UserId(42L))
			.withUsername("user42")
			.withEmail("user42@google.com")
			.withPassword("user42pass")
			.withBio("Hi! i', user42")
			.withImage("https://image.jpeg");
	}

	public static class UserBuilder {
		private UserId userId;
		private String username;
		private String email;
		private String password;
		private String bio;
		private String image;

		public UserBuilder withUserId(UserId userId) {
			this.userId = userId;
			return this;
		}

		public UserBuilder withUsername(String username) {
			this.username = username;
			return this;
		}

		public UserBuilder withEmail(String email) {
			this.email = email;
			return this;
		}

		// TODO: 추후 Test 에서도 Encoding이 필요한지 판단하자
		public UserBuilder withPassword(String password) {
			this.password = password;
			return this;
		}

		public UserBuilder withBio(String bio) {
			this.bio = bio;
			return this;
		}

		public UserBuilder withImage(String image) {
			this.image = image;
			return this;
		}

		public User build() {
			return User.withId(
				this.userId,
				this.username,
				this.email,
				new Password(this.password),
				this.bio,
				this.image);
		}
	}
}
