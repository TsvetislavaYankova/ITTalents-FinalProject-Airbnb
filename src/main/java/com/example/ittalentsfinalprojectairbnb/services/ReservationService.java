package com.example.ittalentsfinalprojectairbnb.services;

import com.example.ittalentsfinalprojectairbnb.exceptions.BadRequestException;
import com.example.ittalentsfinalprojectairbnb.exceptions.NotFoundException;
import com.example.ittalentsfinalprojectairbnb.exceptions.UnauthorizedException;
import com.example.ittalentsfinalprojectairbnb.model.dto.MakePaymentDTO;
import com.example.ittalentsfinalprojectairbnb.model.dto.MakeReservationDTO;
import com.example.ittalentsfinalprojectairbnb.model.dto.PaymentResponseDTO;
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

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

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

    public Reservation makeReservation(MakeReservationDTO dto, Integer userId) {
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

    public Cancellation cancelReservation(int reservationId, int userId) {
        Optional<Reservation> reservation = reservationRepository.findById((reservationId));
        if (reservation.isEmpty()) {
            throw new NotFoundException("There is no such reservation");
        }

        if (reservation.get().getGuestId() != userId) {
            throw new UnauthorizedException("you can't add payment to this reservation!");
        }

        LocalDate checkIn = reservation.get().getCheckInDate();
        LocalDate checkOut = reservation.get().getCheckOutDate();
        int reservationDuration = Period.between(checkIn, checkOut).getDays();
        if (reservationDuration == 0) {
            reservationDuration = 1;
        }

        Property property = propertyRepository.findById(reservation.get().getPropertyId())
                .orElseThrow(() -> new NotFoundException("There is no such property!"));

        double refund = property.getPricePerNight() * (reservationDuration);

        Cancellation cancellation = new Cancellation();
        cancellation.setCancelDate(LocalDate.now());
        cancellation.setRefundAmount(refund);
        cancellationRepository.save(cancellation);

        reservation.get().setCancellation(cancellation);
        reservationRepository.save(reservation.get());

        return cancellation;
    }

    public Reservation getReservationById(int reservationId, int userId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new NotFoundException("There is no suchReservation!"));

        if (reservation.getGuestId() != userId) {
            throw new UnauthorizedException("You can't view this reservation!");
        }
        return reservation;
    }

    public Payment addPayment(MakePaymentDTO paymentDTO, int reservationId, int userId) {

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundException("There is no such reservation!"));

        if (reservation.getGuestId() != userId) {
            throw new UnauthorizedException("you can't add payment to this reservation!");
        }

        LocalDate checkIn = reservation.getCheckInDate();
        LocalDate checkOut = reservation.getCheckOutDate();
        int reservationDuration = Period.between(checkIn, checkOut).getDays();
        if (reservationDuration == 0) {
            reservationDuration = 1;
        }
        Property property = propertyRepository.findById(reservation.getPropertyId())
                .orElseThrow(() -> new NotFoundException("There is no such property!"));

        double price = property.getPricePerNight() * (reservationDuration);

        Payment payment = new Payment();

        payment.setDateOfPayment(LocalDate.now());
        payment.setTotalPrice(price);
        payment.setPaymentType(paymentDTO.getPaymentType());
        payment.setStatus("Waiting confirmation!");

        payment.setReservation(reservation);
        paymentRepository.save(payment);

        return payment;
    }

    public Payment getPaymentById(int paymentId) {
        return paymentRepository.findById(paymentId).orElseThrow(() -> new NotFoundException("There is no such payment!"));
    }

    public Payment confirmPayment(PaymentResponseDTO paymentDTO, int paymentId, int userId) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new NotFoundException("There is no such payment!"));
        payment.setStatus(paymentDTO.getStatus());

        if (payment.getReservation().getGuestId() != userId) {
            throw new UnauthorizedException("you can't confirm this payment!");
        }
        paymentRepository.save(payment);

        return payment;
    }

    private boolean checkReservations(int propertyId, LocalDate checkInDate, LocalDate checkOutDate) {
        boolean isApproved = false;
        if(checkInDate.isAfter(checkOutDate)){
            throw new BadRequestException("The check out date must be after the check in date!");
        }
        
        List<Reservation> reservations = reservationRepository.findAllByPropertyId(propertyId);
        if (!reservations.isEmpty()) {
            for (Reservation reservation : reservations) {
                if (reservation.getCancellation() == null) {
                    isApproved = false;
                    LocalDate checkInDateR = reservation.getCheckInDate();
                    LocalDate checkOutDateR = reservation.getCheckOutDate();

                    if (checkInDate.isEqual(checkInDateR) || checkOutDate.isEqual(checkOutDateR)) {
                        break;
                    }
                    if (checkInDate.isAfter(checkInDateR) && checkOutDate.isBefore(checkOutDateR)) {
                        break;
                    }
                    if (checkOutDate.isAfter(checkInDateR) && checkOutDate.isBefore(checkOutDateR)) {
                        break;
                    }
                    if (checkInDate.isAfter(checkInDateR) && checkInDate.isBefore(checkOutDateR)) {
                        break;
                    }
                    isApproved = true;
                }
            }
        } else {
            isApproved = true;
        }
        return isApproved;
    }
}
