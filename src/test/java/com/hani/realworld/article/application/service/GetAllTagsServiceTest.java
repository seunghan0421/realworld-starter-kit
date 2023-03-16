package com.hani.realworld.article.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.hani.realworld.article.application.port.out.GetAllTagsPort;

class GetAllTagsServiceTest {

	private final GetAllTagsPort getAllTagsPort =
		Mockito.mock(GetAllTagsPort.class);

	private final GetAllTagsService getAllTagsService =
		new GetAllTagsService(getAllTagsPort);

	@Test
	void getProfile_withLogin_Succeeds() {
		// given
		Set<String> tagResponse = Set.of("user1", "user2");

		given(getAllTagsPort.getAllTags())
			.willReturn(tagResponse);

		// when
		Set<String> tags = getAllTagsService.getAllTags();

		// then
		assertThat(tags).contains("user1", "user2").size().isEqualTo(2);

		then(getAllTagsPort).should().getAllTags();
	}

}
