package com.example.ittalentsfinalprojectairbnb.model.repositories;

import com.example.ittalentsfinalprojectairbnb.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);
    User findByPhoneNumber(String phoneNumber);
}
