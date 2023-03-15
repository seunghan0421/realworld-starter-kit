package com.hani.realworld.article.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Slug {

	private final String slug;

	public Slug(final String title) {
		this.slug = toSlug(title);
	}

	private String toSlug(final String title) {
		String slugTitle = title.toLowerCase();
		return slugTitle.replaceAll(" ", "-");
	}
}
