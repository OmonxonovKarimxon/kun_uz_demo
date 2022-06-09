package com.company.dto;

import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDto {
    private Integer id;
    private String name;
    private String Surname;
    private String email;
    private String password;
    private String jwtToken;
    private ProfileRole role;
    private ProfileStatus status;

}
