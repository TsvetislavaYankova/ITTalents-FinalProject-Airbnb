package com.example.ittalentsfinalprojectairbnb.controller;

import com.example.ittalentsfinalprojectairbnb.model.dto.*;
import com.example.ittalentsfinalprojectairbnb.model.entities.Property;
import com.example.ittalentsfinalprojectairbnb.model.entities.PropertyPhoto;
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
    public ResponseEntity<PropertyGetByIdDTO> addProperty(@RequestBody PropertyCreationDTO property, HttpServletRequest request) {
        SessionManager.validateLogin(request);
        PropertyGetByIdDTO dto = propertyService.addProperty(property, (Integer) request.getSession().getAttribute(SessionManager.USER_ID));
        return ResponseEntity.ok(dto);
    }

    @GetMapping(value = "/filter/by/characteristics")
    public ResponseEntity<List<PropertyGetByIdDTO>> filterPropertyByCharacteristics(@RequestBody @NonNull FilterPropertyDTO filter) {
        List<PropertyGetByIdDTO> list = propertyService.filterByCharacteristics(filter);
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/filter/by/price")
    public ResponseEntity<List<PropertyGetByIdDTO>> filterPropertyByPrice(@RequestBody @NonNull PropertyPriceDTO filter) {
        List<PropertyGetByIdDTO> list = propertyService.filterByPrice(filter);
        return ResponseEntity.ok(list);
    }

    @PutMapping("/edit/address/{id}")
    public ResponseEntity<PropertyGetByIdDTO> editAddress(@RequestBody EditAddressDTO addressDTO, HttpServletRequest request,
                                                          @PathVariable int id) {
        SessionManager.validateLogin(request);
        PropertyGetByIdDTO p = propertyService.editAddress(addressDTO, id);
        return ResponseEntity.ok(p);
    }

    @PutMapping("/edit/characteristic/{id}")
    public ResponseEntity<PropertyGetByIdDTO> editCharacteristic(@RequestBody EditCharacteristicDTO characteristicDTO, HttpServletRequest request,
                                                                 @PathVariable int id) {
        SessionManager.validateLogin(request);
        PropertyGetByIdDTO p = propertyService.editCharacteristic(characteristicDTO, id);
        return ResponseEntity.ok(p);
    }

    @PutMapping("/add/rating/{propertyId}")
    public ResponseEntity<PropertyGetByIdDTO> addRating(HttpServletRequest request, @PathVariable int propertyId) {
        SessionManager.validateLogin(request);
        PropertyGetByIdDTO p = propertyService.addRating(propertyId);
        return ResponseEntity.ok(p);
    }

    @DeleteMapping("/delete/{propertyId}")
    public ResponseEntity<String> deleteProperty(HttpServletRequest request, @PathVariable int propertyId) {
        SessionManager.validateLogin(request);
        propertyService.deletePropertyById(propertyId);

        return new ResponseEntity<>("Deletion successful!", HttpStatus.OK);
    }

    @GetMapping("/properties/{propertyId}")
    public ResponseEntity<PropertyGetByIdDTO> getById(@PathVariable int propertyId) {
        Property p = propertyService.getPropertyById(propertyId);

        PropertyGetByIdDTO dto = mapper.map(p, PropertyGetByIdDTO.class);
        dto.setHost_id(p.getHost().getId());
        dto.setAddress_id(p.getAddress().getId());
        if (p.getImages() != null) {
            for (PropertyPhoto ph : p.getImages()) {
                String url = ph.getPhoto_url();
                dto.getPropertyPhotos().add(url);
            }
        }
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
