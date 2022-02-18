package com.example.ittalentsfinalprojectairbnb.controller;

import com.example.ittalentsfinalprojectairbnb.model.dto.*;
import com.example.ittalentsfinalprojectairbnb.model.entities.Cancellation;
import com.example.ittalentsfinalprojectairbnb.model.entities.Reservation;
import com.example.ittalentsfinalprojectairbnb.services.ReservationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
public class ReservationController {

    @Autowired
    private ReservationService service;
    @Autowired
    private ModelMapper mapper;

    @PostMapping("/make_reservation")
    public ResponseEntity<MakeReservationDTO> makeReservation(@RequestBody ReservationPaymentResponseDTO dto,
                                                              HttpSession session) {
        Reservation reservation = service.makeReservation(dto, (Integer) session.getAttribute(UserController.USER_ID));
        MakeReservationDTO dto = mapper.map(reservation, MakeReservationDTO.class);

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/cancel_reservation")
    public ResponseEntity<CancellationResponseDTO> cancelReservation(@RequestBody ReservationResponseDTO reservationDTO, HttpSession session) {
        Cancellation cancellation = service.cancelReservation(reservationDTO, (Integer) session.getAttribute(UserController.USER_ID));
        CancellationResponseDTO dto = mapper.map(cancellation, CancellationResponseDTO.class);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/reservation/{id}")
    public ResponseEntity<ReservationResponseDTO> getById(@PathVariable("id") int id, HttpSession session) {
        Reservation reservation = service.getReservationById(id, (Integer) session.getAttribute(UserController.USER_ID));
        ReservationResponseDTO dto = mapper.map(reservation, ReservationResponseDTO.class);

        return ResponseEntity.ok(dto);
    }
}
