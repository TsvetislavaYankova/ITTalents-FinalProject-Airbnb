package com.example.ittalentsfinalprojectairbnb.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "cancellations")
@Setter
@Getter
@NoArgsConstructor
public class Cancellation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne(mappedBy = "cancellation")
    private Reservation reservation;

    @Column
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate cancelDate;
    @Column
    private double refundAmount;
}
