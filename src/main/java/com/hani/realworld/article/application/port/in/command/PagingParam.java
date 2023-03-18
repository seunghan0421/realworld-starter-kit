package com.hani.realworld.article.application.port.in.command;

import javax.validation.constraints.Min;

import com.hani.realworld.common.util.SelfValidating;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class PagingParam extends SelfValidating<PagingParam> {

	@Min(value = 0, message = "offset must be greater or equals to zero")
	final long offset;

	@Min(value = 1, message = "limit must be greater than zero")
	final long limit;

	public PagingParam() {
		this(0, 20);
	}

	public PagingParam(long offset, long limit) {
		this.offset = offset;
		this.limit = limit;

		this.validateSelf();
	}
}
