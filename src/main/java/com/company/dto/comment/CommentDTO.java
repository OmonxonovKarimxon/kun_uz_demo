package com.company.dto.comment;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDTO {

    private  Integer id;
    private String content;
    private  String articleId;
     private Integer replyId;
     private Integer profileId;

}
