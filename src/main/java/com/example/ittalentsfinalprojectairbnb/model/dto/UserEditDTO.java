package com.example.ittalentsfinalprojectairbnb.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserEditDTO {

    private int id;
    private String firstName;
    private String lastName;
    private char gender;
    private String email;
    private LocalDateTime dateOfBirth;
    private String phoneNumber;
    private boolean isHost;
}
