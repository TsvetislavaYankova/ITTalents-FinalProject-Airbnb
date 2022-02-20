package com.example.ittalentsfinalprojectairbnb.model.dto;

import lombok.Data;

@Data
public class EditCharacteristicDTO {

    private short hasWifi;
    private short hasTv;
    private short hasAirConditioner;
    private short hasFridge;
    private short hasKitchenFacilities;
    private short hasBreakfast;
    private short hasParkingSpot;
    private short hasFitness;
    private short hasWashingMachine;
    private String typeOfBed;
}
