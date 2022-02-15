package com.example.ittalentsfinalprojectairbnb.model.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private int userId;
    @Column
    private int propertyId;
    @Column
    private double rating; //todo
    @Column
    private String review;
}
