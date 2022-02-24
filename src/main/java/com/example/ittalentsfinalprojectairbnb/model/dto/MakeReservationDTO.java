package com.example.ittalentsfinalprojectairbnb.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MakeReservationDTO {

    private int propertyId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}
