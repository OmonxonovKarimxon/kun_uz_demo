package com.company.dto.article;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleTypeDTO {

    private String key;
    private String nameUz;
    private String nameRu;
    private String nameEn;
}
