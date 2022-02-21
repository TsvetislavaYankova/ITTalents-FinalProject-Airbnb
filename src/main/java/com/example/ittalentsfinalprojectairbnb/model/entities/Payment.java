package com.example.ittalentsfinalprojectairbnb.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "payments")
@Setter
@Getter
@NoArgsConstructor
public class Payment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Reservation reservation;
    @Column
    private String paymentType;
    @Column
    private double totalPrice;
    @Column
    private LocalDate dateOfPayment;
    @Column
    private String status;
}
