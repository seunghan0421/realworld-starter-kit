package com.hani.realworld.article.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SlugTest {

	@Test
	void toSlug_test() {
		// given
		String title = "user1 title";
		Slug slug = new Slug(title);

		// when
		slug.toSlug();

		// then
		assertThat(slug.getSlug()).isEqualTo("user1-title");
	}
}
