package com.hani.realworld.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PreConditions {

	/**
	 * Ensures the truth of an expression involving one or more parameters to the calling method.
	 *
	 * @param expression a boolean expression
	 * @throws IllegalArgumentException if {@code expression} is false
	 */
	public static void checkArgument(boolean expression) {
		if (!expression) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Ensures the truth of an expression involving one or more parameters to the calling method.
	 *
	 * @param expression a boolean expression
	 * @param errorMessage the exception message to use if the check fails; will be converted to a
	 *     string using {@link String#valueOf(Object)}
	 * @throws IllegalArgumentException if {@code expression} is false
	 */
	public static void checkArgument(boolean expression, @NonNull Object errorMessage) {
		if (!expression) {
			throw new IllegalArgumentException(String.valueOf(errorMessage));
		}
	}

	/**
	 * Ensures the truth of an expression involving the state of the calling instance, but not
	 * involving any parameters to the calling method.
	 *
	 * @param expression a boolean expression
	 * @throws IllegalStateException if {@code expression} is false
	 */
	public static void checkState(boolean expression) {
		if (!expression) {
			throw new IllegalStateException();
		}
	}

	/**
	 * Ensures the truth of an expression involving the state of the calling instance, but not
	 * involving any parameters to the calling method.
	 *
	 * @param expression a boolean expression
	 * @param errorMessage the exception message to use if the check fails; will be converted to a
	 *     string using {@link String#valueOf(Object)}
	 * @throws IllegalStateException if {@code expression} is false
	 */
	public static void checkState(boolean expression, @NonNull Object errorMessage) {
		if (!expression) {
			throw new IllegalStateException(String.valueOf(errorMessage));
		}
	}

	/**
	 * Ensure that the length of the string is between start and end.
	 *
	 * @param start Minimum length of string
	 * @param end Maximum length of string
	 * @param argument String value
	 * @throws IllegalStateException if {@code isOutOfRange} is false
	 */
	public static void checkStringHasValidLength(int start, int end, @NonNull String argument) {
		checkArgument(isOutOfRange(start, end, argument));
	}

	private static boolean isOutOfRange(int start, int end, String argument) {
		return argument.length() >= start && argument.length() <= end;
	}

}
