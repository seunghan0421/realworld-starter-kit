package com.hani.realworld.user.application.port.out;

import javax.validation.constraints.NotBlank;

import com.hani.realworld.user.domain.User;

public interface LoadUserWithUsernamePort {
	User loadUserWithUsername(@NotBlank String username);
}
