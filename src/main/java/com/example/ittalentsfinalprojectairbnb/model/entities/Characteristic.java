package com.example.ittalentsfinalprojectairbnb.model.entities;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "characteristics")
@Getter
@Setter
@NoArgsConstructor
public class Characteristic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Property property;

    @Column(name = "wifi")
    private short hasWifi;

    @Column(name = "tv")
    private short hasTv;

    @Column(name = "ac")
    private short hasAirConditioner;
    @Column
    private boolean hasFridge;
    @Column
    private boolean hasKitchenFacilities;
    @Column
    private boolean hasBreakfast;
    @Column
    private boolean hasParkingSpot;
    @Column
    private boolean hasFitness;
    @Column
    private boolean hasWashingMachine;
    @Column
    private String typeOfBed;
}

