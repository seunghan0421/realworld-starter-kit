package com.hani.realworld.common.util;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public abstract class SelfValidating<T> {

	private final Validator validator;

	public SelfValidating() {
		try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
			validator = factory.getValidator();
		}
	}

	protected void validateSelf() {
		Set<ConstraintViolation<T>> violations = validator.validate((T)this);

		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(violations);
		}
	}
}
