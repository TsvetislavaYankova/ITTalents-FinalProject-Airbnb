package com.example.ittalentsfinalprojectairbnb.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "properties")
@Getter
@Setter
@NoArgsConstructor
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "host_id")
    private User host;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @Column
    private String propertyType;

    @Column
    private int nOfBedrooms;

    @Column
    private  int nOfBathrooms;

    @Column
    private int nOfGuests;

    @Column
    private double pricePerNight;

    @Column
    private String description;


}
