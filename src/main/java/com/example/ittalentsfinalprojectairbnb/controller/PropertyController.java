package com.example.ittalentsfinalprojectairbnb.controller;

import com.example.ittalentsfinalprojectairbnb.model.dto.*;
import com.example.ittalentsfinalprojectairbnb.model.entities.Property;
import com.example.ittalentsfinalprojectairbnb.services.PropertyService;
import com.example.ittalentsfinalprojectairbnb.utils.SessionManager;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class PropertyController {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private PropertyService propertyService;

    @PostMapping("/add")
    public ResponseEntity<PropertyResponseDTO> addProperty(@RequestBody PropertyCreationDTO property, HttpServletRequest request) {
        SessionManager.validateLogin(request);
        PropertyResponseDTO dto = propertyService.addProperty(property, (Integer) request.getSession().getAttribute(SessionManager.USER_ID));
        return ResponseEntity.ok(dto);
    }

    @GetMapping(value = "/filter/by/characteristics")
    public ResponseEntity<List<PropertyResponseDTO>> filterPropertyByCharacteristics(@RequestBody @NonNull FilterPropertyDTO filter) {
        List<PropertyResponseDTO> list = propertyService.filterByCharacteristics(filter);
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/filter/by/price")
    public ResponseEntity<List<PropertyResponseDTO>> filterPropertyByPrice(@RequestBody @NonNull PropertyPriceDTO filter) {
        List<PropertyResponseDTO> list = propertyService.filterByPrice(filter);
        return ResponseEntity.ok(list);
    }

    @PutMapping("/edit/address/{id}")
    public ResponseEntity<PropertyResponseDTO> editAddress(@RequestBody EditAddressDTO addressDTO, HttpServletRequest request,
                                                           @PathVariable int id) {
        SessionManager.validateLogin(request);
        PropertyResponseDTO p = propertyService.editAddress(addressDTO, id);
        return ResponseEntity.ok(p);
    }

    @PutMapping("/edit/characteristic/{id}")
    public ResponseEntity<PropertyResponseDTO> editCharacteristic(@RequestBody EditCharacteristicDTO characteristicDTO, HttpServletRequest request,
                                                                  @PathVariable int id) {
        SessionManager.validateLogin(request);
        PropertyResponseDTO p = propertyService.editCharacteristic(characteristicDTO, id);
        return ResponseEntity.ok(p);
    }

    @PutMapping("/add/rating/{propertyId}")
    public ResponseEntity<PropertyResponseDTO> addRating(HttpServletRequest request, @PathVariable int propertyId) {
        SessionManager.validateLogin(request);
        PropertyResponseDTO p = propertyService.addRating(propertyId);
        return ResponseEntity.ok(p);
    }

    @DeleteMapping("/delete/{propertyId}")
    public ResponseEntity<String> deleteProperty(HttpServletRequest request, @PathVariable int propertyId) {
        SessionManager.validateLogin(request);
        propertyService.deletePropertyById(propertyId);

        return new ResponseEntity<>("Deletion successful!", HttpStatus.OK);
    }

    @GetMapping("/properties/{propertyId}")
    public ResponseEntity<PropertyResponseDTO> getById(@PathVariable int propertyId) {
        Property p = propertyService.getPropertyById(propertyId);

        PropertyResponseDTO dto = mapper.map(p, PropertyResponseDTO.class);
        dto.additionalMapping(p);
        return ResponseEntity.ok(dto);
    }

    @SneakyThrows
    @PostMapping("/photo/upload/{propertyId}")
    public String uploadImage(@PathVariable int propertyId, @RequestParam(name = "file") MultipartFile file, HttpServletRequest request) {
        SessionManager.validateLogin(request);
        return propertyService.uploadPhoto(propertyId, file, request);
    }

    @DeleteMapping("/photo/delete/{photoId}")
    public ResponseEntity<String> deletePhoto(HttpServletRequest request, @PathVariable int photoId) {
        SessionManager.validateLogin(request);
        propertyService.deletePhotoById(photoId);
        return new ResponseEntity<>("Photo deletion successful!", HttpStatus.OK);
    }
}
