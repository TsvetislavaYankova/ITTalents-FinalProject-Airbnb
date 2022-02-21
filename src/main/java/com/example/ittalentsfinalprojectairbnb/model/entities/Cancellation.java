package com.example.ittalentsfinalprojectairbnb.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "cancellations")
@Setter
@Getter
@NoArgsConstructor
public class Cancellation {

    @Id
    @Column(name = "id")
    private int id;
    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Reservation reservation;
    @Column
    private LocalDate cancelDate;
    @Column
    private double refundAmount;
}
