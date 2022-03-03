package com.example.ittalentsfinalprojectairbnb.controller;

import com.example.ittalentsfinalprojectairbnb.exceptions.NotFoundException;
import com.example.ittalentsfinalprojectairbnb.exceptions.UnauthorizedException;
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
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.nio.file.Files;
import java.util.List;

@RestController
public class PropertyController {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private PropertyService propertyService;

    @PostMapping("/properties/add")
    public ResponseEntity<PropertyResponseDTO> addProperty(@RequestBody PropertyCreationDTO property, HttpServletRequest request) {
        SessionManager.validateLogin(request);
        PropertyResponseDTO dto = propertyService.addProperty(property, (Integer) request.getSession().getAttribute(SessionManager.USER_ID));
        return ResponseEntity.ok(dto);
    }

    @GetMapping(value = "/properties/filter/by/characteristics")
    public ResponseEntity<List<PropertyResponseDTO>> filterPropertyByCharacteristics(@RequestBody @NonNull FilterPropertyDTO filter) {
        List<PropertyResponseDTO> list = propertyService.filterByCharacteristics(filter);
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/properties/filter/by/price")
    public ResponseEntity<List<PropertyResponseDTO>> filterPropertyByPrice(@RequestBody @NonNull PropertyPriceDTO filter) {
        List<PropertyResponseDTO> list = propertyService.filterByPrice(filter);
        return ResponseEntity.ok(list);
    }

    @PutMapping("/properties/edit/address/{id}")
    public ResponseEntity<PropertyResponseDTO> editAddress(@RequestBody EditAddressDTO addressDTO, HttpServletRequest request,
                                                           @PathVariable int id) {
        SessionManager.validateLogin(request);
        validatePropertyHost(request, id);
        PropertyResponseDTO p = propertyService.editAddress(addressDTO, id);
        return ResponseEntity.ok(p);
    }

    @PutMapping("/properties/edit/characteristic/{id}")
    public ResponseEntity<PropertyResponseDTO> editCharacteristic(@RequestBody EditCharacteristicDTO characteristicDTO, HttpServletRequest request,
                                                                  @PathVariable int id) {
        SessionManager.validateLogin(request);
        validatePropertyHost(request, id);
        PropertyResponseDTO p = propertyService.editCharacteristic(characteristicDTO, id);
        return ResponseEntity.ok(p);
    }

    @PutMapping("/properties/add/rating/{propertyId}")
    public ResponseEntity<PropertyResponseDTO> addRating(HttpServletRequest request, @PathVariable int propertyId) {
        SessionManager.validateLogin(request);
        validatePropertyHost(request, propertyId);
        PropertyResponseDTO p = propertyService.addRating(propertyId);
        return ResponseEntity.ok(p);
    }

    @DeleteMapping("/properties/delete/{propertyId}")
    public ResponseEntity<String> deleteProperty(HttpServletRequest request, @PathVariable int propertyId) {
        SessionManager.validateLogin(request);
        validatePropertyHost(request, propertyId);
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
    @PostMapping("/properties/{propertyId}/photo")
    public PropertyPhotoDTO uploadPhoto(@PathVariable int propertyId, @RequestParam(name = "file") MultipartFile file, HttpServletRequest request) {
        SessionManager.validateLogin(request);
        validatePropertyHost(request, propertyId);
        return propertyService.uploadPhoto(propertyId, file);
    }

    @SneakyThrows
    @GetMapping("/properties_photos/{filename}")
    public void downloadPropertyPhoto(@PathVariable String filename, HttpServletResponse response){
        File f = new File("photos/properties_photos" + File.separator + filename);
        if (!f.exists()){
            throw new NotFoundException("File not found!");
        }
        Files.copy(f.toPath(),response.getOutputStream());
    }

    @DeleteMapping("/photos/{photoId}")
    public ResponseEntity<String> deletePhoto(HttpServletRequest request, @PathVariable int photoId) {
        SessionManager.validateLogin(request);

        propertyService.deletePhotoById(request, photoId);
        return new ResponseEntity<>("Photo deletion successful!", HttpStatus.OK);
    }

    public void validatePropertyHost(HttpServletRequest request, int propertyId) {
        if ((int) request.getSession().getAttribute(SessionManager.USER_ID) != propertyService.getPropertyById(propertyId).getHost().getId()) {
            throw new UnauthorizedException("This property belongs to other user!");
        }
    }
}
