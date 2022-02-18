package com.example.ittalentsfinalprojectairbnb.controller;

import com.example.ittalentsfinalprojectairbnb.model.dto.*;
import com.example.ittalentsfinalprojectairbnb.services.PropertyService;
import com.example.ittalentsfinalprojectairbnb.services.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
@RestController
public class PropertyController {

    @Autowired
    private PropertyService propertyService;
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<PropertyIdDTO> addProperty(@RequestBody CreatePropertyDTO property, HttpServletRequest request) {
        UserController.validateLogin(request);
        PropertyIdDTO dto = propertyService.addProperty(property, (Integer) request.getSession().getAttribute(UserController.USER_ID));
        return ResponseEntity.ok(dto);
    }

    @SneakyThrows
    @PostMapping("/image/upload")
    public String uploadImage(@RequestBody PropertyIdDTO propertyID, @RequestParam(name = "file") MultipartFile file, HttpServletRequest request){
        return propertyService.uploadFile(propertyID, file, request);
    }

//    @DeleteMapping("/image/delete")
//    public ResponseEntity<String> deleteUser(HttpSession session){
//        User user = SessionManager.getLoggedUser(session);
//        userRepository.delete(user);
//        return new ResponseEntity<>("Deletion succesful!",HttpStatus.OK);
//    }
}
