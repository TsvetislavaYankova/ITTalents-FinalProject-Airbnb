package com.example.ittalentsfinalprojectairbnb.controller;

import com.example.ittalentsfinalprojectairbnb.model.dto.CancellationResponseDTO;
import com.example.ittalentsfinalprojectairbnb.model.dto.MakeReservationDTO;
import com.example.ittalentsfinalprojectairbnb.model.dto.PaymentResponseDTO;
import com.example.ittalentsfinalprojectairbnb.model.dto.ReservationResponseDTO;
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
    public ResponseEntity<MakeReservationDTO> makeReservation(@RequestBody ReservationResponseDTO dto,
                                                              HttpServletRequest request) {
        SessionManager.validateLogin(request);

        Reservation reservation = service.makeReservation(dto, (Integer) request.getAttribute(SessionManager.USER_ID));
        MakeReservationDTO dto2 = mapper.map(reservation, MakeReservationDTO.class);

        return ResponseEntity.ok(dto2);
    }

    @DeleteMapping("/reservation/cancel_reservation")
    public ResponseEntity<CancellationResponseDTO> cancelReservation(@RequestBody ReservationResponseDTO reservationDTO, HttpServletRequest request) {
        SessionManager.validateLogin(request);

        Cancellation cancellation = service.cancelReservation(reservationDTO);
        CancellationResponseDTO dto = mapper.map(cancellation, CancellationResponseDTO.class);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/reservation/{id}")
    public ResponseEntity<ReservationResponseDTO> getReservationById(@PathVariable("id") int id, HttpServletRequest request) {
        SessionManager.validateLogin(request);

        Reservation reservation = service.getReservationById(id);
        ReservationResponseDTO dto = mapper.map(reservation, ReservationResponseDTO.class);
        return ResponseEntity.ok(dto);
    }


    @PostMapping("/reservation/add_payment")
    public ResponseEntity<PaymentResponseDTO> addPayment(@RequestBody PaymentResponseDTO paymentDTO, HttpServletRequest request) {
        SessionManager.validateLogin(request);

        Payment payment = service.addPayment(paymentDTO);
        PaymentResponseDTO dto = mapper.map(payment, PaymentResponseDTO.class);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/reservation/get_payment/{id}")
    public ResponseEntity<PaymentResponseDTO> getPaymentById(@PathVariable("id") int paymentId, HttpServletRequest request) {
        SessionManager.validateLogin(request);

        Payment payment = service.getPaymentById(paymentId);
        PaymentResponseDTO dto = mapper.map(payment, PaymentResponseDTO.class);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/reservation/get_payment_status/{id}")
    public ResponseEntity<PaymentResponseDTO> getPaymentStatus(@PathVariable("id") int paymentId, HttpServletRequest request) {
        SessionManager.validateLogin(request);

        Payment payment = service.getPaymentById(paymentId);
        PaymentResponseDTO dto = mapper.map(payment, PaymentResponseDTO.class);

        return ResponseEntity.ok(dto);
    }

    @PutMapping("/reservation/confirm_payment")
    public ResponseEntity<PaymentResponseDTO> confirmPayment(@RequestBody PaymentResponseDTO paymentDTO, HttpServletRequest request) {
        SessionManager.validateLogin(request);

        Payment payment = service.confirmPayment(paymentDTO);
        PaymentResponseDTO dto = mapper.map(payment, PaymentResponseDTO.class);

        return ResponseEntity.ok(dto);
    }
}