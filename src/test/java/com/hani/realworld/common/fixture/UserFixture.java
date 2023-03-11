package com.hani.realworld.common.fixture;

import static com.hani.realworld.user.domain.User.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hani.realworld.common.util.PasswordEncoderUtil;
import com.hani.realworld.user.adapter.in.web.dto.LoginUserRequest;
import com.hani.realworld.user.adapter.in.web.dto.RegisterUserRequest;
import com.hani.realworld.user.adapter.in.web.dto.UpdateUserRequest;
import com.hani.realworld.user.domain.Password;
import com.hani.realworld.user.domain.User;

public class UserFixture {

	public static UserBuilder defaultUser() {
		return new UserBuilder()
			.withUserId(new UserId(42L))
			.withUsername("user42")
			.withEmail("user42@google.com")
			.withPassword("password")
			.withBio("Hi! i', user42")
			.withImage("https://image.jpeg");
	}

	public static class UserBuilder {
		private UserId userId;
		private String username;
		private String email;
		private Password password;
		private String bio;
		private String image;
		private String token;

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

		public UserBuilder withPassword(String password) {
			this.password = new Password(password);
			PasswordEncoderUtil.encode(this.password);
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
				this.password,
				this.bio,
				this.image);
		}
	}

	public static final User USER1 = defaultUser()
		.withUserId(new UserId(1L))
		.withUsername("username1")
		.withEmail("user1@naver.com")
		.withBio("im user")
		.withPassword("password1")
		.withImage("http://image.png")
		.build();

	public static final User USER2 = defaultUser()
		.withUserId(new UserId(2L))
		.withUsername("username2")
		.withEmail("user2@naver.com")
		.withBio("im user2")
		.withPassword("password2")
		.withImage("http://image2.png")
		.build();

	public static final RegisterUserRequest REGISTER_USER_REQUEST =
		new RegisterUserRequest(USER1.getUsername(), USER1.getEmail(), "password1");

	public static final LoginUserRequest LOGIN_USER_REQUEST =
		new LoginUserRequest(USER1.getEmail(), "password1");

	public static final UpdateUserRequest UPDATE_USER_REQUEST =
		new UpdateUserRequest(USER2.getEmail(), USER2.getUsername(), "password", USER2.getImage(), USER2.getBio());

}
