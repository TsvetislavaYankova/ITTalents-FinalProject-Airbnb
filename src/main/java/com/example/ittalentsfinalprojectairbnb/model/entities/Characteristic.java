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
    @Column(name = "id")
    private int id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Property propertyCh;

    @Column(name = "wifi")
    private short hasWifi;

    @Column(name = "tv")
    private short hasTv;

    @Column(name = "ac")
    private short hasAirConditioner;

    @Column(name = "fridge")
    private short hasFridge;

    @Column(name = "kitchen_facilities")
    private short hasKitchenFacilities;

    @Column(name = "breakfast")
    private short hasBreakfast;

    @Column(name = "parking_spot")
    private short hasParkingSpot;

    @Column(name = "fitness")
    private short hasFitness;

    @Column(name = "washingmachine")
    private short hasWashingMachine;

    @Column(name = "type_of_bed")
    private String typeOfBed;
}

