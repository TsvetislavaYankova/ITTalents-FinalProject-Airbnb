package com.example.ittalentsfinalprojectairbnb.model.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserEditDTO {

    private String firstName;
    private String lastName;
    private String oldPassword;
    private String newPassword;
    private String confirmedNewPassword;
    private char gender;
    private String email;
    private String phoneNumber;
    private short isHost;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate dateOfBirth;

}
