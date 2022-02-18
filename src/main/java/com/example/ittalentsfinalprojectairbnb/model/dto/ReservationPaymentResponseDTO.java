package com.example.ittalentsfinalprojectairbnb.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationPaymentResponseDTO {

    private int reservationId;
    private int propertyId;
    private int guestId;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private int paymentId;
    private String paymentType;
    private double totalPrice;
    private LocalDateTime dateOfPayment;
    private String status;
}
