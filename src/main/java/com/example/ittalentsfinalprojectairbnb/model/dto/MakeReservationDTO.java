package com.example.ittalentsfinalprojectairbnb.model.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class MakeReservationDTO {

    private int propertyId;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate checkInDate;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate checkOutDate;
}
