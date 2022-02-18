package com.example.ittalentsfinalprojectairbnb.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaymentResponseDTO {

    private int id;
    private String paymentType;
    private double totalPrice;
    private LocalDateTime dateOfPayment;
    private String status;
}
