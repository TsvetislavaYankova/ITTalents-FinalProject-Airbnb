package com.example.ittalentsfinalprojectairbnb.model.entities;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@NoArgsConstructor
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
    @OneToOne(mappedBy = "reservations", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Cancellation cancellation;
}
