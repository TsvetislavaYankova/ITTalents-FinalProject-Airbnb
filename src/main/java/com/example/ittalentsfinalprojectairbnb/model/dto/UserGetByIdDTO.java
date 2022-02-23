package com.example.ittalentsfinalprojectairbnb.model.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserGetByIdDTO {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private char gender;
    private short isHost;
    private String photoUrl;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate dateOfBirth;


}
