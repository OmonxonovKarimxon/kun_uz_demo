package com.company.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDTO {

    private  Integer id;
    private String content;
    private  String articleId;
    private  Integer  profileId;

}
