package com.hani.realworld.profile.application.port.in;

import static com.hani.realworld.profile.domain.User.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.hani.realworld.profile.application.port.in.result.ProfileResult;

public interface GetProfileQuery {
	ProfileResult getProfile(@NotBlank String username, @NotNull UserId userId);
}
