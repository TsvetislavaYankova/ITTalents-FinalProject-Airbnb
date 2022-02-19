package com.example.ittalentsfinalprojectairbnb.model.dto;

import lombok.Data;

@Data
public class UserChangePasswordDTO {

    private int id;
    private String email;
    private String password;
    private String changedPassword;
}
