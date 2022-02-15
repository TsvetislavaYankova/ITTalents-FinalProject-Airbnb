package com.example.ittalentsfinalprojectairbnb.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserGetByIdDTO {

    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private LocalDateTime date_of_birth;
    private char gender;
    private boolean is_host;
    private String photo_url;

}
