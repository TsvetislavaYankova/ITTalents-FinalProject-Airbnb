package com.example.ittalentsfinalprojectairbnb.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "properties_photos")
@Getter
@Setter
@NoArgsConstructor
public class PropertyPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String photo_url;
    @Column
    private int property_id;
}
