package com.example.ittalentsfinalprojectairbnb.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PaymentResponseDTO {

    private int id;
    private String paymentType;
    private double totalPrice;
    private LocalDate dateOfPayment;
    private String status;
}
