package com.example.ittalentsfinalprojectairbnb.controller;

import com.example.ittalentsfinalprojectairbnb.model.dto.UserLogInDTO;
import com.example.ittalentsfinalprojectairbnb.model.dto.UserResponseDTO;
import com.example.ittalentsfinalprojectairbnb.model.entities.Property;
import com.example.ittalentsfinalprojectairbnb.services.PropertyService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

public class PropertyController {

    public static final String PROPERTY_ID = "property_id";

    @Autowired
    private PropertyService service;
    @Autowired
    private ModelMapper mapper;

    @PostMapping("/properties/add")
    public UserResponseDTO add(@RequestBody Property property, HttpServletRequest request) {



        return u;
    }
}
