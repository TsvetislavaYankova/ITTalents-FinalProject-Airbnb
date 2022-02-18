package com.example.ittalentsfinalprojectairbnb.model.dto;

import com.example.ittalentsfinalprojectairbnb.exceptions.BadRequestException;
import lombok.Data;

import java.util.Set;

@Data
public class PropertyCreationDTO {

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
    private Set<String> propertyPhotos;


    public void addressValidation(PropertyCreationDTO propertyDTO) {
        if (propertyDTO.getCity().isBlank() || propertyDTO.getCity() == null) {
            throw new BadRequestException("City is a mandatory field!");
        }
        if (propertyDTO.getCountry().isBlank() || propertyDTO.getCountry() == null) {
            throw new BadRequestException("Country is a mandatory field!");
        }
        if (propertyDTO.getStreet().isBlank() || propertyDTO.getStreet() == null) {
            throw new BadRequestException("Street is a mandatory field!");
        }
        if (propertyDTO.getZipCode().isBlank() || propertyDTO.getZipCode() == null) {
            throw new BadRequestException("Zip code is a mandatory field!");
        }
        if ((Integer) propertyDTO.getApartmentNumber() == null) {
            throw new BadRequestException("Apartment number is a mandatory field!");
        }
    }

    public void propertyValidation(PropertyCreationDTO propertyDTO) {
        if (propertyDTO.getPropertyType().isBlank() || propertyDTO.getZipCode() == null) {
            throw new BadRequestException("Zip code is a mandatory field!");
        }
        if ((Integer) propertyDTO.getBathrooms() == null) {
            throw new BadRequestException("Zip code is a mandatory field!");
        }
        if ((Integer) propertyDTO.getBedrooms() == null) {
            throw new BadRequestException("Zip code is a mandatory field!");
        }
        if ((Integer) propertyDTO.getGuests() == null) {
            throw new BadRequestException("Zip code is a mandatory field!");
        }
        if ((Double) propertyDTO.getPricePerNight() == null) {
            throw new BadRequestException("Zip code is a mandatory field!");
        }
        if (propertyDTO.getDescription().isBlank() || propertyDTO.getDescription() == null) {
            throw new BadRequestException("Zip code is a mandatory field!");
        }
    }
}
