package com.hani.realworld.article.application.port.in.command;

import javax.validation.constraints.Size;

import com.hani.realworld.common.util.SelfValidating;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class GetListArticleCommand extends SelfValidating<GetListArticleCommand> {

	private PagingParam pagingParam;

	/**
	 * About @Size
	 * suppportType
	 * - CharSequence (length of character sequence) Collection (collection size) Map (map size) Array (array length)
	 * - null도 valid로 간주된다.
	 */
	@Size(min = 1, message = "if tag is not null, tag's length must be longer than 1")
	private String tag;

	@Size(min = 1, message = "if author is not null, author's length must be longer than 1")
	private String author;

	@Size(min = 1, message = "if favorited is not null, favorited's length must be longer than 1")
	private String favorited;

	public GetListArticleCommand(PagingParam pagingParam, String tag, String author, String favorited) {
		this.pagingParam = pagingParam;
		this.tag = tag;
		this.author = author;
		this.favorited = favorited;

		this.validateSelf();
	}
}
