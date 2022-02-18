package com.example.ittalentsfinalprojectairbnb.model.dto;

import lombok.Data;

@Data
public class AddReviewDTO {

    private int id;
    private int userId;
    private int propertyId;
    private double rating;
    private String review;
}
