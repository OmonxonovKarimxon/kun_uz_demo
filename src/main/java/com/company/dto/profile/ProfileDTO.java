package com.company.dto.profile;

import com.company.enums.ProfileRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO {

    @NotNull(message = "id is null")
    private Integer id;
    @NotNull(message = "name is null")
    private String name;
    @NotNull(message = "surname is null")
    private String surname;
    @NotNull(message = "email is null")
    private String email;
    @NotNull(message = "role is null")
    private ProfileRole role;
    @NotNull(message = "password is null")
    private String password;

    private String photoId;
    private String jwt;

}
