package com.levi9.socialnetwork.dto;

import lombok.Data;

@Data
public class RegistrationRequestDTO {

    private String name;
    private String surname;
    private String email;
    private String username;
    private String password;
    private String repeatedPassword;
}
