package com.example.ittalentsfinalprojectairbnb.model.dto;

import lombok.Data;

@Data
public class EditAddressDTO {

    private String country;
    private String city;
    private String street;
    private String zipCode;
    private int apartmentNumber;
}
