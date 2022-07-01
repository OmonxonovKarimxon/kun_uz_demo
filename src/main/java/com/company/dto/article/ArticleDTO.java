package com.company.dto.article;

import com.company.dto.category.CategoryDTO;
import com.company.dto.region.RegionDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleDTO {
    @NotNull(message = "ID is not be null")
    private  String id;
    @NotNull(message = "title  is empty")
    private String title;
    @NotNull(message = "content is not be null")
    private String content;
    @NotNull(message = "description is not be null")
    private String description;
    @NotNull(message = "region is not be null")
    private RegionDTO region;
    @NotNull(message = "category is not be null")
    private CategoryDTO category;
    private Integer viewCount;
    @NotNull(message = "publish Date  is not be null")
    private LocalDateTime publishDate;
    private Integer sharedCount;

    private List<Integer> typesList;
    private List<String> tagList;
      private  ArticleLikeDTO like;
}
