package com.company.dto.comment;

import com.company.dto.ProfileDTO;
import com.company.dto.article.ArticleDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentResponseDTO {

    private  Integer id;
    private String content;
    private ArticleDTO article;
     private Integer replyId;
     private ProfileDTO profile;
     private LocalDateTime createDate;
     private  LocalDateTime updateDate;

}
