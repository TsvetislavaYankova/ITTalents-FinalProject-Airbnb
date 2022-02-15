package com.example.ittalentsfinalprojectairbnb.model.dto;

import lombok.Data;

@Data
public class UserRegisterDTO {

    private String email;
    private String password;
    private String verifyPassword;
}
