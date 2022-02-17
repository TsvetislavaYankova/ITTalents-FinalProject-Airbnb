package com.example.ittalentsfinalprojectairbnb.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationGetByIdDTO {

    private int id;
    private int propertyId;
    private int guestId;
    private int paymentId;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
}
