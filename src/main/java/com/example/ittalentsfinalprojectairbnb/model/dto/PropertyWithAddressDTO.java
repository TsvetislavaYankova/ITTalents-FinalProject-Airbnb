package com.example.ittalentsfinalprojectairbnb.model.dto;

import lombok.Data;

@Data
public class PropertyWithAddressDTO {

    private String propertyType;
    private int nOfBedrooms;
    private  int nOfBathrooms;
    private int nOfGuests;
    private double pricePerNight;
    private String description;
    private String country;
    private String city;
    private String street;
    private String zipCode;
    private String apartmentNumber;


}
