package com.example.ittalentsfinalprojectairbnb.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MakeReservationDTO {

    private int id;
    private int guestId;
    private int propertyId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}
