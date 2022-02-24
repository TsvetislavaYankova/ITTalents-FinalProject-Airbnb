package com.example.ittalentsfinalprojectairbnb.controller;

import com.example.ittalentsfinalprojectairbnb.model.dto.*;
import com.example.ittalentsfinalprojectairbnb.model.entities.Cancellation;
import com.example.ittalentsfinalprojectairbnb.model.entities.Payment;
import com.example.ittalentsfinalprojectairbnb.model.entities.Reservation;
import com.example.ittalentsfinalprojectairbnb.services.ReservationService;
import com.example.ittalentsfinalprojectairbnb.utils.SessionManager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ReservationController {

    @Autowired
    private ReservationService service;
    @Autowired
    private ModelMapper mapper;

    @PostMapping("/reservation/make/reservation")
    public ResponseEntity<MakeReservationDTO> makeReservation(@RequestBody MakeReservationDTO reservationDTO,
                                                              HttpServletRequest request) {
        SessionManager.validateLogin(request);

        Reservation reservation = service.makeReservation(reservationDTO, (Integer) request.getSession().getAttribute(SessionManager.USER_ID));
        MakeReservationDTO dto = mapper.map(reservation, MakeReservationDTO.class);

        return ResponseEntity.ok(dto);
    }

    @PostMapping("/reservation/cancel/reservation/{id}")
    public ResponseEntity<CancellationResponseDTO> cancelReservation(@PathVariable("id") int id, HttpServletRequest request) {
        SessionManager.validateLogin(request);

        Cancellation cancellation = service.cancelReservation(id, (Integer) request.getSession().getAttribute(SessionManager.USER_ID));
        CancellationResponseDTO dto = mapper.map(cancellation, CancellationResponseDTO.class);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/reservation/{id}")
    public ResponseEntity<ReservationResponseDTO> getReservationById(@PathVariable("id") int id, HttpServletRequest request) {
        SessionManager.validateLogin(request);

        Reservation reservation = service.getReservationById(id, (Integer) request.getSession().getAttribute(SessionManager.USER_ID));
        ReservationResponseDTO dto = mapper.map(reservation, ReservationResponseDTO.class);
        return ResponseEntity.ok(dto);
    }


    @PostMapping("/reservation/add/payment")
    public ResponseEntity<PaymentResponseDTO> addPayment(@RequestBody MakePaymentDTO paymentDTO, HttpServletRequest request) {
        SessionManager.validateLogin(request);

        Payment payment = service.addPayment(paymentDTO, (Integer) request.getSession().getAttribute(SessionManager.USER_ID));
        PaymentResponseDTO dto = mapper.map(payment, PaymentResponseDTO.class);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/reservation/get/payment/{id}")
    public ResponseEntity<PaymentResponseDTO> getPaymentById(@PathVariable("id") int paymentId, HttpServletRequest request) {
        SessionManager.validateLogin(request);

        Payment payment = service.getPaymentById(paymentId);
        PaymentResponseDTO dto = mapper.map(payment, PaymentResponseDTO.class);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/reservation/get/payment/status/{id}")
    public ResponseEntity<PaymentResponseDTO> getPaymentStatus(@PathVariable("id") int paymentId, HttpServletRequest request) {
        SessionManager.validateLogin(request);

        Payment payment = service.getPaymentById(paymentId);
        PaymentResponseDTO dto = mapper.map(payment, PaymentResponseDTO.class);

        return ResponseEntity.ok(dto);
    }

    @PutMapping("/reservation/confirm/payment/{id}")
    public ResponseEntity<PaymentResponseDTO> confirmPayment(@PathVariable("id") int paymentId,
                                                             @RequestBody PaymentResponseDTO paymentDTO, HttpServletRequest request) {
        SessionManager.validateLogin(request);

        Payment payment = service.confirmPayment(paymentDTO,paymentId);
        PaymentResponseDTO dto = mapper.map(payment, PaymentResponseDTO.class);

        return ResponseEntity.ok(dto);
    }
}