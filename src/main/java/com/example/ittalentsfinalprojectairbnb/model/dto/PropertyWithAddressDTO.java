package com.example.ittalentsfinalprojectairbnb.model.dto;

import lombok.Data;

@Data
public class PropertyWithAddressDTO {

    private String propertyType;
    private int bedrooms;
    private int bathrooms;
    private int guests;
    private double pricePerNight;
    private String description;
    private String country;
    private String city;
    private String street;
    private String zipCode;
    private int apartmentNumber;


}
