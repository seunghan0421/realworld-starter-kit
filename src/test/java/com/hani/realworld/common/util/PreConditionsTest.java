package com.hani.realworld.common.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PreConditionsTest {

	@Test
	void checkArgument_success() {
		assertDoesNotThrow(() -> PreConditions.checkArgument(1 == 1));
	}

	@Test
	void checkArgument_fail() {
		assertThrows(IllegalArgumentException.class,
			() -> PreConditions.checkArgument(1 != 1));
	}

	@Test
	void checkArgumentWITHMESSAGE_success() {
		assertDoesNotThrow(() -> PreConditions.checkArgument(1 == 1, "MESSAGE"));
	}

	@Test
	void checkArgumentWITHMESSAGE_fail() {
		assertThrows(IllegalArgumentException.class,
			() -> PreConditions.checkArgument(1 != 1, "MESSAGE"));
	}

	@Test
	void checkState_success() {
		assertDoesNotThrow(() -> PreConditions.checkState(1 == 1));
	}

	@Test
	void checkState_fail() {
		assertThrows(IllegalStateException.class,
			() -> PreConditions.checkState(1 != 1));
	}

	@Test
	void checkStateWITHMESSAGE_success() {
		assertDoesNotThrow(() -> PreConditions.checkState(1 == 1, "MESSAGE"));
	}

	@Test
	void checkStateWITHMESSAGE_fail() {
		assertThrows(IllegalStateException.class,
			() -> PreConditions.checkState(1 != 1, "MESSAGE"));
	}

	@Test
	void checkStringHasValidLength_success() {
		assertDoesNotThrow(() -> PreConditions.checkStringHasValidLength(0, 3, "yes"));
	}

	@Test
	void checkStringHasValidLength_failure() {
		assertThrows(IllegalArgumentException.class,
			() -> PreConditions.checkStringHasValidLength(1, 2, "String"));
	}
}
