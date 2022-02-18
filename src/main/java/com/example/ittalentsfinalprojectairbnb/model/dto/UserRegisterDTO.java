package com.example.ittalentsfinalprojectairbnb.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserRegisterDTO {

    private String email;
    private String password;
    private String confirmedPassword;
    private String firstName;
    private String lastName;
    private char gender;
    private LocalDateTime dateOfBirth;
    private String phoneNumber;
    private short isHost;
}
