package com.example.ittalentsfinalprojectairbnb.model.dto;

import lombok.Data;

@Data
public class FilterPropertyDTO {

    private String country;
    private String city;
    private int bedrooms;
    private int bathrooms;
    private int guests;
    private short hasWifi;
    private short hasTv;
    private short hasAirConditioner;
    private short hasFridge;
    private short hasKitchenFacilities;
    private short hasBreakfast;
    private short hasParkingSpot;
    private short hasFitness;
    private short hasWashingMachine;
}
