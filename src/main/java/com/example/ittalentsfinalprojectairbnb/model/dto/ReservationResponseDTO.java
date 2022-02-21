package com.example.ittalentsfinalprojectairbnb.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservationResponseDTO {

    private int id;
    private int propertyId;
    private int guestId;
    private int paymentId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}
