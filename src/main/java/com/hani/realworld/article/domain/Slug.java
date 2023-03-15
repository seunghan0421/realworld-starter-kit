package com.hani.realworld.article.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Slug {

	private String slug;

	public Slug(final String title) {
		this.slug = title;
	}

	public void toSlug() {
		String slugTitle = slug.toLowerCase();
		this.slug = slugTitle.replaceAll(" ", "-");
	}
}
