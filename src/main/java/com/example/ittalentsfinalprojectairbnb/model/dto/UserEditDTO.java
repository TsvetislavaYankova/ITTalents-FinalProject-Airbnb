package com.example.ittalentsfinalprojectairbnb.model.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserEditDTO {

    private int id;
    private String firstName;
    private String lastName;
    private String password;
    private String confirmedPassword;
    private char gender;
    private String email;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private short isHost;
}
