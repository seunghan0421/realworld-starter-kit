package com.hani.realworld.common.fixture;

import static com.hani.realworld.common.fixture.UserFixture.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.*;

import org.mockito.Mockito;

import com.hani.realworld.user.domain.User;

public class UserServiceFixture {

	public static User givenAnUserWithUser1() {
		User user = Mockito.mock(User.class);

		given(user.getId()).willReturn(USER1.getId());
		given(user.getUsername()).willReturn(USER1.getUsername());
		given(user.getEmail()).willReturn(USER1.getEmail());
		given(user.getBio()).willReturn(USER1.getBio());
		given(user.getImage()).willReturn(USER1.getImage());

		return user;
	}

	public static User givenAnUserWithUser2() {
		User user = Mockito.mock(User.class);

		given(user.getId()).willReturn(USER2.getId());
		given(user.getUsername()).willReturn(USER2.getUsername());
		given(user.getEmail()).willReturn(USER2.getEmail());
		given(user.getBio()).willReturn(USER2.getBio());
		given(user.getImage()).willReturn(USER2.getImage());

		return user;
	}
}
