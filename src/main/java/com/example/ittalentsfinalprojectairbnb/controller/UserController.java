package com.example.ittalentsfinalprojectairbnb.controller;

import com.example.ittalentsfinalprojectairbnb.exceptions.UnauthorizedException;
import com.example.ittalentsfinalprojectairbnb.model.dto.*;
import com.example.ittalentsfinalprojectairbnb.model.entities.User;
import com.example.ittalentsfinalprojectairbnb.services.UserService;
import com.example.ittalentsfinalprojectairbnb.utils.SessionManager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@RestController
public class UserController {

    @Autowired
    private UserService service;
    @Autowired
    private ModelMapper mapper;


    @PostMapping("/login")
    public UserResponseDTO login(@RequestBody UserLogInDTO user, HttpServletRequest request) {

        UserResponseDTO u = service.login(user.getEmail(), user.getPassword());
        request.getSession().setAttribute(SessionManager.LOGGED, true);
        request.getSession().setAttribute(SessionManager.LOGGED_FROM, request.getRemoteAddr());
        request.getSession().setAttribute(SessionManager.USER_ID, u.getId());

        return u;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserRegisterDTO user) {

        UserResponseDTO u = service.register(user.getEmail(), user.getPassword(), user.getConfirmedPassword(),
                user.getFirstName(), user.getLastName(), user.getGender(), user.getDateOfBirth(), user.getPhoneNumber(), user.getIsHost());

        return ResponseEntity.ok(u);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return new ResponseEntity<>("Logged out successfully!", HttpStatus.OK);
    }

    @PostMapping("/add_photo")
    public ResponseEntity<UserResponseDTO> addPhoto(@RequestBody UserGetByIdDTO userDTO, HttpServletRequest request) {
        SessionManager.validateLogin(request);
        User user = service.addPhoto(userDTO);

        UserResponseDTO dto = mapper.map(user, UserResponseDTO.class);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/delete_user")
    public ResponseEntity<String> deleteUser(HttpServletRequest request) {
        SessionManager.validateLogin(request);
        int userId = (Integer) request.getSession().getAttribute(SessionManager.USER_ID);
        service.deleteById(userId);
        request.getSession().invalidate();

        return new ResponseEntity<>("Deletion successful!", HttpStatus.OK);
    }

    @DeleteMapping("/delete_photo/{id}")
    public ResponseEntity<UserResponseDTO> deletePhoto(@PathVariable("id") int id, HttpServletRequest request) {
        SessionManager.validateLogin(request);
        User user = service.deletePhotoById(id);
        UserResponseDTO dto = mapper.map(user, UserResponseDTO.class);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/get_user/{id}")
    public ResponseEntity<UserGetByIdDTO> getById(@PathVariable("id") int id, HttpServletRequest request) {
        SessionManager.validateLogin(request);

        User user = service.getUserById(id);
        UserGetByIdDTO dto = mapper.map(user, UserGetByIdDTO.class);

        return ResponseEntity.ok(dto);
    }

    @PutMapping("/edit")
    public ResponseEntity<UserEditDTO> edit(@RequestBody UserEditDTO userDTO, HttpServletRequest request){
        SessionManager.validateLogin(request);
        User user = service.edit(userDTO);
        UserEditDTO dto =mapper.map(user,UserEditDTO.class);

        return ResponseEntity.ok(dto);
    }

}