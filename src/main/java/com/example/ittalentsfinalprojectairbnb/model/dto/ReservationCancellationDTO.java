package com.example.ittalentsfinalprojectairbnb.model.dto;

import lombok.Data;

@Data
public class ReservationCancellationDTO {

    private String message = "Reservation cancelled";
    private int id;
    private int guestId;
}
