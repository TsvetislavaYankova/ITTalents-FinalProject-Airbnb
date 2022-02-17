package com.example.ittalentsfinalprojectairbnb.model.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
public class Payment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String paymentType;

    @Column
    private double totalPrice;

    @Column
    private LocalDateTime dateOfPayment;

    @Column
    private String status;
}
