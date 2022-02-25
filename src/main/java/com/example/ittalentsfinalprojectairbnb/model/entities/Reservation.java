package com.example.ittalentsfinalprojectairbnb.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

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
    private LocalDate checkInDate;
    @Column
    private LocalDate checkOutDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "reservations_have_cancellations",
            joinColumns =
                    {@JoinColumn(name = "reservation_id",
                            referencedColumnName = "id")},
            inverseJoinColumns =
                    {@JoinColumn(name = "cancellation_id",
                            referencedColumnName = "id")})
    private Cancellation cancellation;

    @OneToOne(mappedBy = "reservation", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Payment payment;

    public void setPayment(Payment payment) {
        if (payment != null) {

            payment.setReservation(this);
        }
        this.payment = payment;
    }

    public void setCancellation(Cancellation cancellation) {
        if (cancellation != null) {

            cancellation.setReservation(this);
        }
        this.cancellation = cancellation;
    }
}
