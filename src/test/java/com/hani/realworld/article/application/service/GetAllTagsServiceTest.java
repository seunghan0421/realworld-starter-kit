package com.hani.realworld.article.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.hani.realworld.article.application.port.out.GetAllTagsPort;

class GetAllTagsServiceTest {

	private final GetAllTagsPort getAllTagsPort =
		Mockito.mock(GetAllTagsPort.class);

	private final GetAllTagsService getAllTagsService =
		new GetAllTagsService(getAllTagsPort);

	@DisplayName("태그 리스트 조회 서비스 테스트 - 성공, 로그인 되어있는 상태")
	@Test
	void getProfile_withLogin_Succeeds() {
		// given
		Set<String> tagResponse = Set.of("user1", "user2");

		given(getAllTagsPort.getAllTags()).willReturn(tagResponse);

		// when
		Set<String> tags = getAllTagsService.getAllTags();

		// then
		assertThat(tags).contains("user1", "user2").size().isEqualTo(2);

		then(getAllTagsPort).should().getAllTags();
	}

}
