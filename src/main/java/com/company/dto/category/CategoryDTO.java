package com.company.dto.category;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDTO {
    @NotNull(message = "ID is not  be null")
    private Integer id;
    @NotNull(message = "key is not be null ")
    private String key;

    private String nameUz;

    private String nameRu;

    private String nameEn;

    private String lang;
}
