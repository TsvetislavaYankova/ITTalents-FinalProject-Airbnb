package com.example.ittalentsfinalprojectairbnb.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

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

    @Column(name="n_of_bedrooms")
    private int bedrooms;

    @Column(name = "n_of_bathrooms")
    private int bathrooms;

    @Column(name = "n_of_guests")
    private int guests;

    @Column
    private double pricePerNight;

    @Column
    private String description;

    @OneToMany(mappedBy = "property")
    private Set<PropertyPhoto> images;

}
