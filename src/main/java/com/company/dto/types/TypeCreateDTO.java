package com.company.dto.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)

public class TypeCreateDTO {

    @NotNull(message = "key is not be null ")
    private String key;
    @NotNull(message = "nameUz is not be null ")
    private String nameUz;
    @NotNull(message = "nameRu is not be null ")
    private String nameRu;
    @NotNull(message = "nameEn is not be null ")
    private String nameEn;





}
