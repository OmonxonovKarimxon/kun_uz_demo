package com.company.dto.article;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleSaveDTO {
    @NotNull(message = "id is not be null")
    private Integer id;
    @NotNull(message = "article id is not be null")
    private String ArticleId;
}
