package com.example.ittalentsfinalprojectairbnb.model.dto;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Data
public class UserRegisterDTO {

    private String email;
    private String password;
    private String confirmedPassword;
    private String firstName;
    private String lastName;
    private char gender;
    private String phoneNumber;
    private short isHost;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate dateOfBirth;



}
