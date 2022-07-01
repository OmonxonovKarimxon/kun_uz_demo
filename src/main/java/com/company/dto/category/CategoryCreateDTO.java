package com.company.dto.category;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryCreateDTO {
    @NotNull(message = "key is not be null ")
    private String key;
    @NotNull(message = "nameUz is not be null ")
     private String nameUz;
    @NotNull(message = "nameRu is not be null ")
    private String nameRu;
    @NotNull(message = "nameEn is not be null ")
    private String nameEn;


}
