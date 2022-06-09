package com.company.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDto {
    private Integer id;
    private String nameUZ;
    private String nameRU;
    private String nameENG;
    private String key;





}
