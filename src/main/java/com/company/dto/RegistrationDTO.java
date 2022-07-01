package com.company.dto;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class RegistrationDTO {
    @NotBlank(message = "name is null")
    private String name;
    @NotBlank(message = "name is null")
    private String surname;
    @NotBlank(message = "email is null")
    @Email
    private String email;
    @NotBlank(message = "password is null")
    private String password;
    @NotBlank(message = "phone is null")
    private String phone;
    private String photoId;

}