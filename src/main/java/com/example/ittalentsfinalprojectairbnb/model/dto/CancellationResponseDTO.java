package com.example.ittalentsfinalprojectairbnb.model.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class CancellationResponseDTO {

    private int id;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate cancelDate;
    private double refundAmount;
}
