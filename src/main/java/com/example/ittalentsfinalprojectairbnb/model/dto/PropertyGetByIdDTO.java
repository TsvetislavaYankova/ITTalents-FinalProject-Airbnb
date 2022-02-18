package com.example.ittalentsfinalprojectairbnb.model.dto;

import lombok.Data;

import java.util.Set;

@Data
public class PropertyGetByIdDTO {

    private int id;
    private int host_id;
    private int address_id;
    private String propertyType;
    private int bedrooms;
    private int bathrooms;
    private int guests;
    private double pricePerNight;
    private String description;
    private Set<String> propertyPhotos;
}
