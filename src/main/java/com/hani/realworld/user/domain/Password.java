package com.hani.realworld.user.domain;

import static com.hani.realworld.common.util.PreConditions.*;

import java.util.function.Function;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Password {

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
