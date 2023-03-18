package com.hani.realworld.article.application.port.in;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public interface DeleteArticleUseCase {
	void deleteArticle(@NotBlank String slug, @NotNull Long userId);
}
