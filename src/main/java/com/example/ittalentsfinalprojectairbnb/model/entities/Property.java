package com.example.ittalentsfinalprojectairbnb.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @Column(name = "id")
    private int id;

    @OneToOne(mappedBy = "propertyAd", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Address address;

    @OneToOne(mappedBy = "propertyCh", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Characteristic characteristic;

    @ManyToOne
    @JoinColumn(name = "host_id")
    private User host;

    @Column
    private String propertyType;

    @Column(name = "n_of_bedrooms")
    private int bedrooms;

    @Column(name = "n_of_bathrooms")
    private int bathrooms;

    @Column(name = "n_of_guests")
    private int guests;

    @Column
    private double pricePerNight;

    @Column
    private String description;

    @Column
    private double guestRating;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    private Set<PropertyPhoto> images;


    public void setAddress(Address address) {
        if (address != null) {

            address.setPropertyAd(this);
        }
        this.address = address;
    }

    public void setCharacteristic(Characteristic characteristic) {
        if (characteristic != null) {

            characteristic.setPropertyCh(this);
        }
        this.characteristic = characteristic;
    }

}
