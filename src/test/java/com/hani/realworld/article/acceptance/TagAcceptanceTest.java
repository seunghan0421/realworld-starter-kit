package com.hani.realworld.article.acceptance;

import static com.hani.realworld.common.fixture.ArticleFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.*;
import static org.springframework.test.annotation.DirtiesContext.*;

import java.util.stream.Stream;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.test.annotation.DirtiesContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hani.realworld.AcceptanceTest;
import com.hani.realworld.article.adapter.in.dto.response.TagResponse;

@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
class TagAcceptanceTest extends AcceptanceTest {

	@DisplayName("태그 기능 Acceptance Test")
	@TestFactory
	Stream<DynamicTest> manageTag() throws JsonProcessingException {

		createArticle(ARTICLE1);

		return Stream.of(
			dynamicTest("태그 조회", () -> {
				TagResponse response = get("/api/tags", HttpStatus.SC_OK,
					TagResponse.class);
				assertThat(response.getTags()).hasSize(ARTICLE1.getTags().getTagSet().size());
			})
		);
	}
}
