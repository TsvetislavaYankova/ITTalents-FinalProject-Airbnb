package com.example.ittalentsfinalprojectairbnb.model.dto;

import lombok.Data;

@Data
public class MakePaymentDTO {

    private int propertyId;
    private String paymentType;
    private double totalPrice;
}
