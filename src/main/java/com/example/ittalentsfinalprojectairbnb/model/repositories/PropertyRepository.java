package com.example.ittalentsfinalprojectairbnb.model.repositories;

import com.example.ittalentsfinalprojectairbnb.model.entities.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> {

    Property getPropertyById(int id);

}
