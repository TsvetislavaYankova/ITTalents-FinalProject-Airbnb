package com.example.ittalentsfinalprojectairbnb.controller;

import com.example.ittalentsfinalprojectairbnb.model.dto.MakeReservationDTO;
import com.example.ittalentsfinalprojectairbnb.model.dto.ReservationCancellationDTO;
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
    public ResponseEntity<MakeReservationDTO> makeReservation(@RequestBody Reservation reservation, HttpSession session) {
        int id = reservation.getId();
        Reservation r = service.makeReservation(id, (Integer) session.getAttribute(UserController.USER_ID));
        MakeReservationDTO dto = mapper.map(r, MakeReservationDTO.class);

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/cancel_reservation")
    public ResponseEntity<ReservationCancellationDTO> cancelReservation(@RequestBody Reservation reservation, HttpSession session) {
        int id = reservation.getId();

        Reservation r = service.cancelReservation(id, (Integer) session.getAttribute(UserController.USER_ID));

        ReservationCancellationDTO dto = mapper.map(r, ReservationCancellationDTO.class);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/reservation/{id}")
    public ResponseEntity<ReservationCancellationDTO> getById(@PathVariable("id") int id, HttpSession session) {

        Reservation r = service.getReservationById(id, (Integer) session.getAttribute(UserController.USER_ID));

        ReservationCancellationDTO dto = mapper.map(r, ReservationCancellationDTO.class);

        return ResponseEntity.ok(dto);
    }
}
