package com.example.ittalentsfinalprojectairbnb.model.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class PaymentResponseDTO {

    private int id;
    private String paymentType;
    private double totalPrice;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate dateOfPayment;
    private String status;
}
