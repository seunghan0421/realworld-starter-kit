package com.hani.realworld.user.application.port.in;

import java.util.Optional;

import javax.validation.constraints.NotBlank;

import com.hani.realworld.user.application.port.in.result.ProfileResult;

public interface GetProfileQuery {
	ProfileResult getProfile(@NotBlank String username, Optional<Long> userId);
}
