package com.example.ittalentsfinalprojectairbnb.model.dto;

import com.example.ittalentsfinalprojectairbnb.model.entities.Address;
import com.example.ittalentsfinalprojectairbnb.model.entities.Characteristic;
import com.example.ittalentsfinalprojectairbnb.model.entities.Property;
import com.example.ittalentsfinalprojectairbnb.model.entities.PropertyPhoto;
import lombok.Data;

import java.util.Set;

@Data
public class PropertyResponseDTO {

    private int id;
    private int host_id;
    private String propertyType;
    private int bedrooms;
    private int bathrooms;
    private int guests;
    private double pricePerNight;
    private String description;
    private double guestRating;
    private Set<String> propertyPhotos;

    private String country;
    private String city;
    private String street;
    private String zipCode;
    private int apartmentNumber;

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

    public void additionalMapping(Property property) {
        this.setHost_id(property.getHost().getId());

        if (property.getImages() != null) {
            for (PropertyPhoto ph : property.getImages()) {
                String url = ph.getPhoto_url();
                this.getPropertyPhotos().add(url);
            }
        }

        this.setCountry(property.getAddress().getCountry());
        this.setCity(property.getAddress().getCity());
        this.setStreet(property.getAddress().getStreet());
        this.setZipCode(property.getAddress().getZipCode());
        this.setApartmentNumber(property.getAddress().getApartmentNumber());

        this.setTypeOfBed(property.getCharacteristic().getTypeOfBed());
        this.setHasWashingMachine((property.getCharacteristic().getHasWashingMachine()));
        this.setHasFitness(property.getCharacteristic().getHasFitness());
        this.setHasParkingSpot(property.getCharacteristic().getHasParkingSpot());
        this.setHasBreakfast(property.getCharacteristic().getHasBreakfast());
        this.setHasKitchenFacilities(property.getCharacteristic().getHasKitchenFacilities());
        this.setHasFridge(property.getCharacteristic().getHasFridge());
        this.setHasAirConditioner(property.getCharacteristic().getHasAirConditioner());
        this.setHasTv(property.getCharacteristic().getHasTv());
        this.setHasWifi(property.getCharacteristic().getHasWifi());
    }
}
