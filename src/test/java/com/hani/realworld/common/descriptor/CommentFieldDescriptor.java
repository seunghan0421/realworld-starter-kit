package com.hani.realworld.common.descriptor;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

public class CommentFieldDescriptor {

	public static final FieldDescriptor[] comment = new FieldDescriptor[] {
		fieldWithPath("id").type(JsonFieldType.NUMBER).description("댓글 식별번호"),
		fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성 일자"),
		fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("수정 일자"),
		fieldWithPath("body").type(JsonFieldType.STRING).description("본문"),
		fieldWithPath("author").type(JsonFieldType.OBJECT).description("작성자 프로필")
	};
}
