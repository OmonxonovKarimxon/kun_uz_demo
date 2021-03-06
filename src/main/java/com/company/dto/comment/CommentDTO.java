package com.company.dto.comment;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDTO {
    @NotNull(message = "id is not be null")
    private Integer id;
    @NotNull(message = "content is not be null")
    private String content;
    @NotNull(message = "Article id is not be null")
    private String articleId;
    @NotNull(message = "reply id is not be null")
    private Integer replyId;


}
