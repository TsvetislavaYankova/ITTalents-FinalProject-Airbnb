package com.example.ittalentsfinalprojectairbnb.model.repositories;

import com.example.ittalentsfinalprojectairbnb.model.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Integer> {

     Review getById(int id);

     @Override
     void deleteById(Integer integer);
}