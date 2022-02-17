package com.example.ittalentsfinalprojectairbnb.services;

import com.example.ittalentsfinalprojectairbnb.exceptions.NotFoundException;
import com.example.ittalentsfinalprojectairbnb.exceptions.UnauthorizedException;
import com.example.ittalentsfinalprojectairbnb.model.entities.Reservation;
import com.example.ittalentsfinalprojectairbnb.model.repositories.ReservationRepository;
import com.example.ittalentsfinalprojectairbnb.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private UserRepository userRepository;

    public Reservation makeReservation(int ReservationId, Integer userId) {
        validateUser(userId);

        Reservation r = new Reservation();
        r.setGuestId(userId);
        r.setCheckInDate(LocalDateTime.now());
        r.setCheckOutDate(LocalDateTime.now());
        r.setPropertyId(1);//todo

        return r;
    }


    public Reservation cancelReservation(int reservationId, Integer userId) {
        validateUser(userId);
        validateReservation(reservationId);

        Reservation r = new Reservation();
        r.setId(reservationId);
        r.setGuestId(userId);

        reservationRepository.deleteById(reservationId);

        return r;
    }


    public Reservation getReservationById(int reservationId, Integer userId) {
        validateUser(userId);
        validateReservation(reservationId);

        Reservation r = reservationRepository.findReservationById(reservationId);

        return r;
    }

    private void validateUser(Integer userId) {
        if (userId == null) {
            throw new UnauthorizedException("To make a reservation you have to be logged in!");
        }

        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("This user doesn't exist");
        }
    }

    private void validateReservation(int reservationId) {
        if (!reservationRepository.existsById(reservationId)) {
            throw new NotFoundException("There is no such reservation");
        }
    }
}
