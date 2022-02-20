package com.example.ittalentsfinalprojectairbnb.controller;

import com.example.ittalentsfinalprojectairbnb.model.dto.*;
import com.example.ittalentsfinalprojectairbnb.model.entities.Property;
import com.example.ittalentsfinalprojectairbnb.model.entities.PropertyPhoto;
import com.example.ittalentsfinalprojectairbnb.services.PropertyService;
import com.example.ittalentsfinalprojectairbnb.services.UserService;
import com.example.ittalentsfinalprojectairbnb.utils.SessionManager;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
public class PropertyController {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private PropertyService propertyService;
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<PropertyIdDTO> addProperty(@RequestBody PropertyCreationDTO property, HttpServletRequest request) {
        SessionManager.validateLogin(request);
        PropertyIdDTO dto = propertyService.addProperty(property, (Integer) request.getSession().getAttribute(SessionManager.USER_ID));
        return ResponseEntity.ok(dto);
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
        if (p.getImages() != null) {
            for (PropertyPhoto ph : p.getImages()) {
                String url = ph.getPhoto_url();
                dto.getPropertyPhotos().add(url);
            }
        }
        dto.setAddress_id(p.getAddress().getId());
        dto.setHost_id(p.getHost().getId());
        return ResponseEntity.ok(dto);
    }

    @SneakyThrows
    @PostMapping("/image/upload")
    public String uploadImage(@RequestBody PropertyIdDTO propertyID, @RequestParam(name = "file") MultipartFile
            file, HttpServletRequest request) {
        SessionManager.validateLogin(request);
        return propertyService.uploadFile(propertyID, file, request);
    }

    @DeleteMapping("/image/delete/{id}")
    public ResponseEntity<String> deleteUser(HttpServletRequest request, @PathVariable int id) {
        SessionManager.validateLogin(request);
        propertyService.deletePhotoById(id);
        return new ResponseEntity<>("Deletion successful!", HttpStatus.OK);
    }
}
