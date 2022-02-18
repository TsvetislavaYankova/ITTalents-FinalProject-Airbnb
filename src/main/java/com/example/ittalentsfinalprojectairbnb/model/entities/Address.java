package com.example.ittalentsfinalprojectairbnb.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
public class Address {

    @Id
    @Column(name = "id")
    private int id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Property propertyAd;

    @Column
    private String country;

    @Column
    private String city;

    @Column
    private String street;

    @Column
    private String zipCode;

    @Column
    private int apartmentNumber;
}
