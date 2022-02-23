package com.example.ittalentsfinalprojectairbnb.services;

import com.example.ittalentsfinalprojectairbnb.exceptions.BadRequestException;
import com.example.ittalentsfinalprojectairbnb.exceptions.NotFoundException;
import com.example.ittalentsfinalprojectairbnb.model.dto.PaymentResponseDTO;
import com.example.ittalentsfinalprojectairbnb.model.dto.ReservationResponseDTO;
import com.example.ittalentsfinalprojectairbnb.model.entities.Cancellation;
import com.example.ittalentsfinalprojectairbnb.model.entities.Payment;
import com.example.ittalentsfinalprojectairbnb.model.entities.Property;
import com.example.ittalentsfinalprojectairbnb.model.entities.Reservation;
import com.example.ittalentsfinalprojectairbnb.model.repositories.CancellationRepository;
import com.example.ittalentsfinalprojectairbnb.model.repositories.PaymentRepository;
import com.example.ittalentsfinalprojectairbnb.model.repositories.PropertyRepository;
import com.example.ittalentsfinalprojectairbnb.model.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private CancellationRepository cancellationRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    public Reservation makeReservation(ReservationResponseDTO dto, Integer userId) {
        int propertyId = dto.getPropertyId();

        propertyRepository.findById(propertyId).orElseThrow(() -> new NotFoundException("There is no such property!"));

        LocalDate checkInDate = dto.getCheckInDate();
        LocalDate checkOutDate = dto.getCheckOutDate();

        if (!checkReservations(propertyId, checkInDate, checkOutDate)) {
            throw new BadRequestException("You can't make reservations in this period!");
        }

        Reservation reservation = new Reservation();
        reservation.setPropertyId(propertyId);
        reservation.setGuestId(userId);
        reservation.setCheckInDate(checkInDate);
        reservation.setCheckOutDate(checkOutDate);

        reservationRepository.save(reservation);

        return reservation;
    }

    public Cancellation cancelReservation(ReservationResponseDTO dto) {
        int reservationId = dto.getId();

        reservationRepository.findById(reservationId).orElseThrow(() -> new NotFoundException("There is no such reservation!"));

        LocalDate checkIn = dto.getCheckInDate();
        LocalDate checkOut = dto.getCheckOutDate();
        long reservationDuration = Duration.between(checkOut, checkIn).toDays();

        int propertyId = dto.getPropertyId();
        Property property = propertyRepository.findById(propertyId).orElseThrow(() -> new NotFoundException("There is no such property!"));

        double refund = property.getPricePerNight() * (reservationDuration);

        Cancellation cancellation = new Cancellation();
        cancellation.setId(reservationId);
        cancellation.setCancelDate(LocalDate.now());
        cancellation.setRefundAmount(refund);

        cancellationRepository.save(cancellation);

        return cancellation;
    }

    public Reservation getReservationById(int reservationId) {
        return reservationRepository.findById(reservationId).orElseThrow(() -> new NotFoundException("There is no suchReservation!"));
    }

    public Payment addPayment(PaymentResponseDTO paymentDTO) {
        Payment payment = new Payment();

        payment.setDateOfPayment(paymentDTO.getDateOfPayment());
        payment.setTotalPrice(paymentDTO.getTotalPrice());
        payment.setPaymentType(paymentDTO.getPaymentType());
        payment.setStatus(paymentDTO.getStatus());

        paymentRepository.save(payment);

        return payment;
    }

    public Payment getPaymentById(int paymentId) {
        return paymentRepository.findById(paymentId).orElseThrow(() -> new NotFoundException("There is no such payment!"));
    }

    public Payment confirmPayment(PaymentResponseDTO paymentDTO) {
        int paymentId = paymentDTO.getId();

        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new NotFoundException("There is no such payment!"));
        payment.setStatus(paymentDTO.getStatus());

        paymentRepository.save(payment);

        return payment;
    }

    private boolean checkReservations(int propertyId, LocalDate checkInDate, LocalDate checkOutDate) {
        boolean isApproved = false;
        List<Reservation> reservations = reservationRepository.findAllByPropertyId(propertyId);
        if (!reservations.isEmpty()) {
            for (Reservation reservation : reservations) {
                LocalDate checkInDateR = reservation.getCheckInDate();
                LocalDate checkOutDateR = reservation.getCheckOutDate();

                if (checkInDate.isEqual(checkInDateR)) {
                    continue;
                } else if (checkInDate.isAfter(checkInDateR) && checkInDate.isBefore(checkOutDateR)) {
                    continue;
                } else if (checkOutDate.isAfter(checkInDateR)) {
                    continue;
                } else {
                    isApproved = true;
                }
            }
        } else {
            isApproved =true;
        }
        return isApproved;
    }
}
