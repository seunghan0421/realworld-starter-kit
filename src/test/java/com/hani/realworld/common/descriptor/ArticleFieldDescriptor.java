package com.hani.realworld.common.descriptor;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

public final class ArticleFieldDescriptor {

	public static final FieldDescriptor[] article = new FieldDescriptor[] {
		fieldWithPath("slug").type(JsonFieldType.STRING).description("슬러그"),
		fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
		fieldWithPath("description").type(JsonFieldType.STRING).description("설명"),
		fieldWithPath("body").type(JsonFieldType.STRING).description("본문"),
		fieldWithPath("tagList").type(JsonFieldType.ARRAY).description("태그 리스트"),
		fieldWithPath("createdAt").type(JsonFieldType.STRING).description("작성일자"),
		fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("수정일자"),
		fieldWithPath("favorited").type(JsonFieldType.BOOLEAN).description("즐겨찾기 여부"),
		fieldWithPath("favoritesCount").type(JsonFieldType.NUMBER).description("즐겨찾기 수"),
		fieldWithPath("author").type(JsonFieldType.OBJECT).description("작성자 프로필")
	};
}
