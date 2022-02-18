package com.example.ittalentsfinalprojectairbnb.model.dto;

import com.example.ittalentsfinalprojectairbnb.exceptions.BadRequestException;
import lombok.Data;

import javax.persistence.Column;
import java.util.Set;

@Data
public class PropertyCreationDTO {
    //property
    private String propertyType;
    private int bedrooms;
    private int bathrooms;
    private int guests;
    private double pricePerNight;
    private String description;
    private Set<String> propertyPhotos;

    //address
    private String country;
    private String city;
    private String street;
    private String zipCode;
    private int apartmentNumber;


    //characteristic
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

    //validations
    public void addressValidation() {
        if (this.getCity().isBlank() || this.getCity() == null) {
            throw new BadRequestException("City is a mandatory field!");
        }
        if (this.getCountry().isBlank() || this.getCountry() == null) {
            throw new BadRequestException("Country is a mandatory field!");
        }
        if (this.getStreet().isBlank() || this.getStreet() == null) {
            throw new BadRequestException("Street is a mandatory field!");
        }
        if (this.getZipCode().isBlank() || this.getZipCode() == null) {
            throw new BadRequestException("Zip code is a mandatory field!");
        }
        if (this.getApartmentNumber() < 1) {
            throw new BadRequestException("Invalid apartment number!");
        }
    }

    public void propertyValidation() {
        if (this.getPropertyType().isBlank() || this.getZipCode() == null) {
            throw new BadRequestException("Property type is a mandatory field!");
        }
        if (this.getBathrooms() < 1) {
            throw new BadRequestException("Invalid number of bathrooms!");
        }
        if (this.getBedrooms() < 1) {
            throw new BadRequestException("Invalid number of bedrooms!");
        }
        if (this.getGuests() < 1) {
            throw new BadRequestException("Invalid number of guests!");
        }
        if (this.getPricePerNight() < 0.0) {
            throw new BadRequestException("Invalid price per night!");
        }
        if (this.getDescription().isBlank() || this.getDescription() == null) {
            throw new BadRequestException("Description is a mandatory field!");
        }
    }

    public void characteristicValidation() {
        if (this.getTypeOfBed().isBlank() || this.getTypeOfBed() == null) {
            throw new BadRequestException("Type of bed is a mandatory field!");
        }
        if (!(this.hasWashingMachine == 1 || this.hasWashingMachine == 0)) {
            throw new BadRequestException("Invalid washing machine characteristic information");
        }
        if (!(this.hasAirConditioner == 1 || this.hasAirConditioner == 0)) {
            throw new BadRequestException("Invalid air conditioner characteristic information");
        }
        if (!(this.hasBreakfast == 1 || this.hasBreakfast == 0)) {
            throw new BadRequestException("Invalid breakfast characteristic information");
        }
        if (!(this.hasFitness == 1 || this.hasFitness == 0)) {
            throw new BadRequestException("Invalid fitness characteristic information");
        }
        if (!(this.hasFridge == 1 || this.hasFridge == 0)) {
            throw new BadRequestException("Invalid fridge characteristic information");
        }
        if (!(this.hasParkingSpot == 1 || this.hasParkingSpot == 0)) {
            throw new BadRequestException("Invalid parking spot characteristic information");
        }
        if (!(this.hasTv == 1 || this.hasTv == 0)) {
            throw new BadRequestException("Invalid TV characteristic information");
        }
        if (!(this.hasKitchenFacilities == 1 || this.hasKitchenFacilities == 0)) {
            throw new BadRequestException("Invalid kitchen facilities characteristic information");
        }
        if (!(this.hasWifi == 1 || this.hasWifi == 0)) {
            throw new BadRequestException("Invalid wi-fi characteristic information");
        }
    }
}
