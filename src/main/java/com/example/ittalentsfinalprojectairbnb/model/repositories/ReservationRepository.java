package com.example.ittalentsfinalprojectairbnb.model.repositories;

import com.example.ittalentsfinalprojectairbnb.model.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    Reservation findReservationById(Integer id);//todo optional and use orElseThrow when used
    @Override
    void deleteById(Integer integer);
}
