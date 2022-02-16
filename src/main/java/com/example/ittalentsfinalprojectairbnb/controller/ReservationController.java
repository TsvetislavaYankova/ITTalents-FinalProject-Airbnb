package com.example.ittalentsfinalprojectairbnb.controller;

import com.example.ittalentsfinalprojectairbnb.model.dto.ReservationResponseDTO;
import com.example.ittalentsfinalprojectairbnb.model.entities.Reservation;
import com.example.ittalentsfinalprojectairbnb.services.ReservationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/reservation")
public class ReservationController {

    @Autowired
    private ReservationService service;
    @Autowired
    private ModelMapper mapper;

//    @PostMapping("/make_reservation")
//    public ReservationResponseDTO makeReservation(@RequestBody Reservation reservation){
//        int id = reservation.getId();
//
//    }
}
