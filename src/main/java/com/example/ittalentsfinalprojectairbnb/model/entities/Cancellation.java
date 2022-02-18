package com.example.ittalentsfinalprojectairbnb.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private LocalDateTime cancelDate;
    @Column
    private double refundAmount;
}
