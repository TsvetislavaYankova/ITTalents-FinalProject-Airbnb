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
    @Column
    private int propertyId;
    @Column
    private boolean hasWifi;
    @Column
    private boolean hasTv;
    @Column
    private boolean hasAc;
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
