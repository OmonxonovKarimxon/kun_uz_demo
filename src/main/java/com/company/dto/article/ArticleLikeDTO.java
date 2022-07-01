package com.company.dto.article;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ArticleLikeDTO {
    @NotNull(message = "id is not be null")
    private String articleId;
    private Integer likeCount = 0;
    private Integer dislikeCount = 0;
}
