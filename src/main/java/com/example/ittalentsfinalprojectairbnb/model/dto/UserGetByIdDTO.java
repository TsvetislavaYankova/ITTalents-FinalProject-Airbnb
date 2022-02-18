package com.example.ittalentsfinalprojectairbnb.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserGetByIdDTO {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDateTime dateOfBirth;
    private char gender;
    private short isHost;
    private String photoUrl;

}
