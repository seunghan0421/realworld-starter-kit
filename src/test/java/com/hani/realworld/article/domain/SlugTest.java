package com.hani.realworld.article.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SlugTest {

	@DisplayName("슬러지 변환 도메인 테스트 - 실패, 이미 팔로우, slug.toSlug()")
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
