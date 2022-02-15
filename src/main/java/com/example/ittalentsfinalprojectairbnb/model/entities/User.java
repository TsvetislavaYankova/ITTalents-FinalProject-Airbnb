package com.example.ittalentsfinalprojectairbnb.model.entities;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String first_name;
    @Column
    private String last_name;
    @Column
    private char gender;
    @Column
    private LocalDateTime date_of_birth;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String phone_number;
    @Column
    private String photo_url;
    @Column
    private boolean is_host;

}
