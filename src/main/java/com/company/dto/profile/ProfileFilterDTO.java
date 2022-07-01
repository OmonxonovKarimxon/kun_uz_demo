package com.company.dto.profile;

import com.company.enums.ProfileRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileFilterDTO {
    private Integer id;
    private String name;
    private String surname;
    private String email;
    private ProfileRole role;
    private  String RegisterDateFrom;
    private  String RegisterDateTo;




}
