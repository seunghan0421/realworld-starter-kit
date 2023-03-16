package com.hani.realworld.article.adapter.in;

import static org.mockito.BDDMockito.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;

import com.hani.realworld.article.application.port.in.GetAllTagsUseCase;
import com.hani.realworld.common.restdocs.ControllerTest;

@WebMvcTest(TagController.class)
class TagControllerTest extends ControllerTest {

	@MockBean
	private GetAllTagsUseCase getAllTagsUseCase;

	@Test
	void getAllTags_Succeeds() throws Exception {
		Set<String> tagResponse = Set.of("user1", "user2");

		given(getAllTagsUseCase.getAllTags())
			.willReturn(tagResponse);

		mockMvc.perform(
				RestDocumentationRequestBuilders.get("/api/tags")
					.accept(APPLICATION_JSON_VALUE)
					.contentType(APPLICATION_JSON_VALUE)
			)
			.andExpect(status().isOk())
			.andDo(
				restDocs.document(
					responseFields(
						fieldWithPath("tags").type(JsonFieldType.ARRAY).description("태그 리스트")
					)
				)
			);

		then(getAllTagsUseCase).should().getAllTags();
	}

}
