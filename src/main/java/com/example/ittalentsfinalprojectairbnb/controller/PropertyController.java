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
    public ResponseEntity<PropertyIdDTO> addProperty(@RequestBody PropertyCreationDTO property, HttpServletRequest request) {
        SessionManager.validateLogin(request);
        PropertyIdDTO dto = propertyService.addProperty(property, (Integer) request.getSession().getAttribute(SessionManager.USER_ID));
        return ResponseEntity.ok(dto);
    }

    @GetMapping(value = "/filter/by/characteristics")
    public ResponseEntity<List<PropertyIdDTO>> filterPropertyByCharacteristics(@RequestBody @NonNull FilterPropertyDTO filter) {
        List<PropertyIdDTO> list = propertyService.filterByCharacteristics(filter);
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/filter/by/price")
    public ResponseEntity<List<PropertyIdDTO>> filterPropertyByPrice(@RequestBody @NonNull PropertyPriceDTO filter) {
        List<PropertyIdDTO> list = propertyService.filterByPrice(filter);
        return ResponseEntity.ok(list);
    }

    @PutMapping("/edit/address/{id}")
    public ResponseEntity<PropertyIdDTO> editAddress(@RequestBody EditAddressDTO addressDTO, HttpServletRequest request,
                                                     @PathVariable int id) {
        SessionManager.validateLogin(request);
        PropertyIdDTO p = propertyService.editAddress(addressDTO, id);
        return ResponseEntity.ok(p);
    }

    @PutMapping("/edit/characteristic/{id}")
    public ResponseEntity<PropertyIdDTO> editCharacteristic(@RequestBody EditCharacteristicDTO characteristicDTO, HttpServletRequest request,
                                                            @PathVariable int id) {
        SessionManager.validateLogin(request);
        PropertyIdDTO p = propertyService.editCharacteristic(characteristicDTO, id);
        return ResponseEntity.ok(p);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProperty(HttpServletRequest request, @PathVariable int id) {
        SessionManager.validateLogin(request);
        propertyService.deletePropertyById(id);

        return new ResponseEntity<>("Deletion successful!", HttpStatus.OK);
    }

    @GetMapping("/properties/{id}")
    public ResponseEntity<PropertyGetByIdDTO> getById(@PathVariable int id) {
        Property p = propertyService.getPropertyById(id);

        PropertyGetByIdDTO dto = mapper.map(p, PropertyGetByIdDTO.class);
        dto.setHost_id(p.getHost().getId());
        dto.setAddress_id(p.getAddress().getId());
        return ResponseEntity.ok(dto);
    }

    @SneakyThrows
    @PostMapping("/photo/upload")
    public String uploadImage(@RequestBody PropertyIdDTO propertyID, @RequestParam(name = "file") MultipartFile file, HttpServletRequest request) {
        SessionManager.validateLogin(request);
        return propertyService.uploadPhoto(propertyID, file, request);
    }

    @DeleteMapping("/photo/delete/{id}")
    public ResponseEntity<String> deleteUser(HttpServletRequest request, @PathVariable int id) {
        SessionManager.validateLogin(request);
        propertyService.deletePhotoById(id);
        return new ResponseEntity<>("Photo deletion successful!", HttpStatus.OK);
    }
}
