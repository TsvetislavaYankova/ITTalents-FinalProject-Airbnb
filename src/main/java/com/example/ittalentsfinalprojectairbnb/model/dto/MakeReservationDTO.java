package com.example.ittalentsfinalprojectairbnb.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MakeReservationDTO {

    private int id;
    private int guestId;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private int propertyId;
}
