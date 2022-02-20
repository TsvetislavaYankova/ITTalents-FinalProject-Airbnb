package com.example.ittalentsfinalprojectairbnb.controller;

import com.example.ittalentsfinalprojectairbnb.model.dto.CancellationResponseDTO;
import com.example.ittalentsfinalprojectairbnb.model.dto.MakeReservationDTO;
import com.example.ittalentsfinalprojectairbnb.model.dto.ReservationPaymentResponseDTO;
import com.example.ittalentsfinalprojectairbnb.model.dto.ReservationResponseDTO;
import com.example.ittalentsfinalprojectairbnb.model.entities.Cancellation;
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

    @PostMapping("/make_reservation")
    public ResponseEntity<MakeReservationDTO> makeReservation(@RequestBody ReservationPaymentResponseDTO dto,
                                                              HttpServletRequest request) {
        //service.makeReservation(mapper.map(dto.getKey(), MakeReservationDTO.class)
        SessionManager.validateLogin(request);

        Reservation reservation = service.makeReservation(dto, (Integer) request.getAttribute(SessionManager.USER_ID));
        MakeReservationDTO dto2 = mapper.map(reservation, MakeReservationDTO.class);

        return ResponseEntity.ok(dto2);
    }

    @DeleteMapping("/cancel_reservation")
    public ResponseEntity<CancellationResponseDTO> cancelReservation(@RequestBody ReservationResponseDTO reservationDTO, HttpServletRequest request) {
        SessionManager.validateLogin(request);

        Cancellation cancellation = service.cancelReservation(reservationDTO, (Integer) request.getAttribute(SessionManager.USER_ID));
        CancellationResponseDTO dto = mapper.map(cancellation, CancellationResponseDTO.class);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/reservation/{id}")
    public ResponseEntity<ReservationResponseDTO> getById(@PathVariable("id") int id, HttpServletRequest request) {
        SessionManager.validateLogin(request);

        Reservation reservation = service.getReservationById(id);
        ReservationResponseDTO dto = mapper.map(reservation, ReservationResponseDTO.class);
        return ResponseEntity.ok(dto);
    }
}
