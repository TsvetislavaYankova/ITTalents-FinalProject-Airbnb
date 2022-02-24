package com.example.ittalentsfinalprojectairbnb.model.dto;

import lombok.Data;

@Data
public class AddReviewDTO {

    private int propertyId;
    private double rating;
    private String comment;
}
