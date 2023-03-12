package com.hani.realworld.user.application.port.in;

import static com.hani.realworld.user.domain.User.*;

import java.util.Optional;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.hani.realworld.user.application.port.in.result.ProfileResult;

public interface GetProfileQuery {
	ProfileResult getProfile(@NotBlank String username, Optional<Long> userId);
}
