package com.example.ittalentsfinalprojectairbnb.model.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private int propertyId;
    @Column
    private int guestId;
    @Column
    private int paymentId;
    @Column
    private LocalDateTime checkInDate;
    @Column
    private LocalDateTime checkOutDate;
}
