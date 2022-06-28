package com.company.dto.article;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class ArticleCreateDTO {
    @NotNull(message = "mazgi title qani")
    private String title;
    @NotBlank(message = "content yoqku")
    private String content;
    @NotBlank(message= "mxb j fkvbd")
    private String description;

    private Integer regionId;
    private Integer categoryId;

    private List<Integer> typesList;
    private List<String> tagList;
}
