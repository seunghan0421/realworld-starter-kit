package com.hani.realworld.user.domain;

import static com.hani.realworld.common.util.PreConditions.*;

import java.util.function.Function;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Password {

	// TODO: 이거는 DTO에서 입력 받고록 변경
	// public static int MIN_LENGTH = 1;
	// public static int MAX_LENGTH = 16;

	private String value;

	public Password(final String password) {

		this.value = password;
	}

	/**
	 * encrypts the string stored in the value with the input passwordEncoder.
	 */
	public void encode(final Function<String, String> passwordEncoder) {
		this.value = passwordEncoder.apply(this.value);
	}

}
