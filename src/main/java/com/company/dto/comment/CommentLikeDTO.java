package com.company.dto.comment;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CommentLikeDTO {
    @NotNull(message = "comment Id  is not be null")
    private Integer commentId;
}
