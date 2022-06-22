package com.company.dto.article;

import com.company.dto.CategoryDTO;
import com.company.dto.RegionDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleDTO {
    private  String id;
    private String title;
    private String content;
    private String description;

    private RegionDTO region;
    private CategoryDTO category;
    private Integer viewCount;
    private LocalDateTime publishDate;
    private Integer sharedCount;

    private List<Integer> typesList;
    private List<String> tagList;
      private  ArticleLikeDTO like;
}
