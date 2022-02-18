package com.example.ittalentsfinalprojectairbnb.services;

import com.example.ittalentsfinalprojectairbnb.exceptions.NotFoundException;
import com.example.ittalentsfinalprojectairbnb.exceptions.UnauthorizedException;
import com.example.ittalentsfinalprojectairbnb.model.entities.Reservation;
import com.example.ittalentsfinalprojectairbnb.model.repositories.PropertyRepository;
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
    @Autowired
    private PropertyRepository propertyRepository;

    public Reservation makeReservation(int propertyId, Integer userId) {
        validateUser(userId);
        validateProperty(propertyId);

        Reservation reservation = new Reservation();
        reservation.setGuestId(userId);
        reservation.setCheckInDate(LocalDateTime.now());
        reservation.setCheckOutDate(LocalDateTime.now());
        reservation.setPropertyId(propertyId);
        reservationRepository.save(reservation);

        return reservation;
    }

    public Reservation cancelReservation(int reservationId, Integer userId) {
        validateUser(userId);
        validateReservation(reservationId);

        Reservation reservation = new Reservation();
        reservation.setId(reservationId);
        reservation.setGuestId(userId);

        reservationRepository.deleteById(reservationId);

        return reservation;
    }


    public Reservation getReservationById(int reservationId, Integer userId) {
        validateUser(userId);
        validateReservation(reservationId);

        Reservation reservation = reservationRepository.findReservationById(reservationId);

        return reservation;
    }

    private void validateUser(Integer userId) {
        if (userId == null) {
            throw new UnauthorizedException("To make a reservation you have to be logged in!");
        }

        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("This user doesn't exist!");
        }
    }

    private void validateReservation(int reservationId) {
        if (!reservationRepository.existsById(reservationId)) {
            throw new NotFoundException("There is no such reservation!");
        }
    }

    private void validateProperty(int propertyId) {
        if(!propertyRepository.existsById(propertyId)){
            throw new NotFoundException("This property doesn't exist!");
        }
    }

}
