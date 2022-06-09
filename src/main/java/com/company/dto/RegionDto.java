package com.company.dto;

import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegionDto {
    private Integer id;
    private String nameUZ;
    private String nameRU;
    private String nameENG;
    private String key;





}
