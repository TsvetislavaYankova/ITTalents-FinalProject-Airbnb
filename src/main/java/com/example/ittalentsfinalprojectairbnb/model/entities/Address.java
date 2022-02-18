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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Property property;
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
