package com.example.ittalentsfinalprojectairbnb.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CancellationResponseDTO {

    private int id;
    private LocalDateTime cancelDate;
    private double refundAmount;
}
