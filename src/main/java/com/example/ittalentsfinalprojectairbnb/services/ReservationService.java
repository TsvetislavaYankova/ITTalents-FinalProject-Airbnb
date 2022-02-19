package com.example.ittalentsfinalprojectairbnb.services;

import com.example.ittalentsfinalprojectairbnb.exceptions.NotFoundException;
import com.example.ittalentsfinalprojectairbnb.exceptions.UnauthorizedException;
import com.example.ittalentsfinalprojectairbnb.model.dto.ReservationPaymentResponseDTO;
import com.example.ittalentsfinalprojectairbnb.model.dto.ReservationResponseDTO;
import com.example.ittalentsfinalprojectairbnb.model.entities.Cancellation;
import com.example.ittalentsfinalprojectairbnb.model.entities.Payment;
import com.example.ittalentsfinalprojectairbnb.model.entities.Property;
import com.example.ittalentsfinalprojectairbnb.model.entities.Reservation;
import com.example.ittalentsfinalprojectairbnb.model.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private CancellationRepository cancellationRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    //check if property is booked
    public Reservation makeReservation(ReservationPaymentResponseDTO dto,
                                       Integer userId) {//todo (Reservation reservation, Payment payment, userID)
        int propertyId = dto.getPropertyId();

        validateUser(userId);
        validateProperty(propertyId);

        Payment payment = new Payment();
        payment.setPaymentType(dto.getPaymentType());
        payment.setTotalPrice(payment.getTotalPrice());
        payment.setDateOfPayment(dto.getDateOfPayment());
        payment.setStatus(dto.getStatus());

        paymentRepository.save(payment);

        Reservation reservation = new Reservation();
        reservation.setPropertyId(propertyId);
        reservation.setGuestId(userId);
        reservation.setPaymentId(payment.getId());
        reservation.setCheckInDate(dto.getCheckInDate());
        reservation.setCheckOutDate(dto.getCheckOutDate());

        reservationRepository.save(reservation);

        return reservation;
    }

    public Cancellation cancelReservation(ReservationResponseDTO dto, Integer userId) {
        int reservationId = dto.getId();

        validateUser(userId);
        validateReservation(reservationId);

        LocalDateTime checkIn = dto.getCheckInDate();
        LocalDateTime checkOut = dto.getCheckOutDate();
        long reservationDuration = Duration.between(checkOut, checkIn).toDays();

        int propertyId = dto.getPropertyId();
        validateProperty(propertyId);
        Property property = propertyRepository.getPropertyById(propertyId);
        double refund = property.getPricePerNight() * (reservationDuration);

        Cancellation cancellation = new Cancellation();
        cancellation.setId(reservationId);
        cancellation.setCancelDate(LocalDateTime.now());
        cancellation.setRefundAmount(refund);
//todo
        cancellationRepository.save(cancellation);

        return cancellation;
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
        if (!propertyRepository.existsById(propertyId)) {
            throw new NotFoundException("This property doesn't exist!");
        }
    }
}
