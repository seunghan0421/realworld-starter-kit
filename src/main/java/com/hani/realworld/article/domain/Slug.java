package com.hani.realworld.article.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Slug {

	private String value;

	public Slug(final String title) {
		this.value = title;
	}

	public void toSlug() {
		this.value = value.toLowerCase().replaceAll(" ", "-");
	}
}
