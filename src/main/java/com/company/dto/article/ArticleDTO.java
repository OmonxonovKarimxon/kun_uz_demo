package com.company.dto.article;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleDTO {
    private  String id;
    private String title;
    private String content;
    private String description;

    private Integer regionId;
    private Integer categoryId;
    private Integer likeCount;
    private Integer viewCount;

    private List<Integer> typesList;
    private List<String> tagList;

}
