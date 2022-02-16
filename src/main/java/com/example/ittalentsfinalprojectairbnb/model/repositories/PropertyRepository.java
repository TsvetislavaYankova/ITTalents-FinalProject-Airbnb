package com.example.ittalentsfinalprojectairbnb.model.repositories;

import com.example.ittalentsfinalprojectairbnb.model.entities.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Integer> {

}
