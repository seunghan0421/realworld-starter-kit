package com.hani.realworld.common.fixture;

import static com.hani.realworld.user.domain.User.*;
import static org.mockito.BDDMockito.*;

import org.mockito.Mockito;

import com.hani.realworld.common.util.PasswordEncoderUtil;
import com.hani.realworld.infra.jwt.LoginToken;
import com.hani.realworld.user.adapter.in.web.dto.request.LoginUserRequest;
import com.hani.realworld.user.adapter.in.web.dto.request.UpdateUserRequest;
import com.hani.realworld.user.adapter.in.web.dto.response.RegisterUserRequest;
import com.hani.realworld.user.domain.Password;
import com.hani.realworld.user.domain.User;

public final class UserFixture {

	public static UserBuilder defaultUser() {
		return new UserBuilder()
			.withUserId(new UserId(42L))
			.withUsername("user42")
			.withEmail("user42@google.com")
			.withPassword("password")
			.withBio("Hi! i', user42")
			.withImage("https://image.jpeg");
	}

	public static final User USER1 = defaultUser()
		.withUserId(new UserId(1L))
		.withUsername("username1")
		.withEmail("user1@naver.com")
		.withBio("im user1")
		.withPassword("password1")
		.withImage("http://image1.png")
		.build();

	public static final User USER2 = defaultUser()
		.withUserId(new UserId(2L))
		.withUsername("username2")
		.withEmail("user2@naver.com")
		.withBio("im user2")
		.withPassword("password2")
		.withImage("http://image2.png")
		.build();

	public static final User REGISTER_USER = defaultUser()
		.withUserId(USER1.getId())
		.withUsername(USER1.getUsername())
		.withEmail(USER1.getEmail())
		.withBio(null)
		.withPassword("password1")
		.withImage(null)
		.build();

	public static User getMockUSER1() {
		User user = Mockito.mock(User.class);

		given(user.getId()).willReturn(USER1.getId());
		given(user.getUsername()).willReturn(USER1.getUsername());
		given(user.getEmail()).willReturn(USER1.getEmail());
		given(user.getBio()).willReturn(USER1.getBio());
		given(user.getImage()).willReturn(USER1.getImage());

		return user;
	}

	public static final RegisterUserRequest REGISTER_USER_REQUEST =
		new RegisterUserRequest(REGISTER_USER.getUsername(), REGISTER_USER.getEmail(), "password");

	public static final LoginUserRequest LOGIN_USER_REQUEST =
		new LoginUserRequest(USER1.getEmail(), "password1");

	public static final UpdateUserRequest UPDATE_USER_REQUEST =
		new UpdateUserRequest(USER2.getEmail(), USER2.getUsername(), "password2", USER2.getImage(), USER2.getBio());

	public static final LoginToken USER1_LOGIN_TOKEN =
		new LoginToken(USER1.getId().getValue(), "user1 jwt token", USER1.getEmail());

	public static final LoginToken USER2_LOGIN_TOKEN =
		new LoginToken(USER2.getId().getValue(), "user2 jwt token", USER2.getEmail());

	public static class UserBuilder {
		private UserId userId;
		private String username;
		private String email;
		private Password password;
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

}
