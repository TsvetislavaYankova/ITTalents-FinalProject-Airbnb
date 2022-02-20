package com.example.ittalentsfinalprojectairbnb.services;

import com.example.ittalentsfinalprojectairbnb.exceptions.NotFoundException;
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
import java.util.List;

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

    //todo check if property is booked
    public Reservation makeReservation(ReservationPaymentResponseDTO dto,
                                       Integer userId) {//todo (Reservation reservation, Payment payment, userID)?
        int propertyId = dto.getPropertyId();

        propertyRepository.findById(propertyId).orElseThrow(() -> new NotFoundException("There is no such property!"));

        LocalDateTime checkInDate = dto.getCheckInDate();
        LocalDateTime checkOutDate = dto.getCheckOutDate();

        checkReservations(propertyId, checkInDate, checkOutDate);

        Payment payment = new Payment();
//        payment.setPaymentType(dto.getPaymentType());
//        payment.setTotalPrice(payment.getTotalPrice());
//        payment.setDateOfPayment(dto.getDateOfPayment());
//        payment.setStatus(dto.getStatus());

        paymentRepository.save(payment);

        Reservation reservation = new Reservation();
        reservation.setPropertyId(propertyId);
        reservation.setGuestId(userId);
        reservation.setPaymentId(payment.getId());
        reservation.setCheckInDate(checkInDate);
        reservation.setCheckOutDate(checkOutDate);

        reservationRepository.save(reservation);

        return reservation;
    }

    public Cancellation cancelReservation(ReservationResponseDTO dto, Integer userId) {
        int reservationId = dto.getId();

        reservationRepository.findById(reservationId).orElseThrow(() -> new NotFoundException("There is no such reservation!"));

        LocalDateTime checkIn = dto.getCheckInDate();
        LocalDateTime checkOut = dto.getCheckOutDate();
        long reservationDuration = Duration.between(checkOut, checkIn).toDays();

        int propertyId = dto.getPropertyId();
        Property property = propertyRepository.findById(propertyId).orElseThrow(() -> new NotFoundException("There is no such property!"));

        double refund = property.getPricePerNight() * (reservationDuration);

        Cancellation cancellation = new Cancellation();
        cancellation.setId(reservationId);
        cancellation.setCancelDate(LocalDateTime.now());
        cancellation.setRefundAmount(refund);

        cancellationRepository.save(cancellation);

        return cancellation;
    }

    public Reservation getReservationById(int reservationId) {
        return reservationRepository.findById(reservationId).orElseThrow(() -> new NotFoundException("There is no suchReservation!"));
    }

    private void checkReservations(int propertyId, LocalDateTime checkInDate, LocalDateTime checkOutDate) {
        List<Reservation> reservations = reservationRepository.findAllByPropertyId(propertyId);

        for (int i = 0; i < reservations.size(); i++) {
            LocalDateTime checkInDate1 = reservations.get(i).getCheckInDate();
            LocalDateTime checkOutDate1 = reservations.get(i).getCheckOutDate();
            //todo
        }
    }
}
