package com.example.ittalentsfinalprojectairbnb.controller;

import com.example.ittalentsfinalprojectairbnb.model.dto.*;
import com.example.ittalentsfinalprojectairbnb.model.entities.Property;
import com.example.ittalentsfinalprojectairbnb.services.PropertyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
@RestController
public class PropertyController {

    @Autowired
    private PropertyService service;

    @PostMapping("/add")
    public ResponseEntity<PropertyIdDTO> addProperty(@RequestBody PropertyWithAddressDTO property, HttpServletRequest request) {
        UserController.validateLogin(request);
        PropertyIdDTO dto = service.addProperty(property, (Integer) request.getSession().getAttribute(UserController.USER_ID));
        return ResponseEntity.ok(dto);
    }

//    @PostMapping("/addresses")
//    public ResponseEntity<AddressIdDTO> addAddress(@RequestBody AddressDTO address, HttpServletRequest request) {
//        UserController.validateLogin(request);
//
//        AddressIdDTO ad = service.addAddress(address);
//
//        return ResponseEntity.ok(ad);
//    }
}
