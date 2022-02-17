package com.example.ittalentsfinalprojectairbnb.controller;

import com.example.ittalentsfinalprojectairbnb.exceptions.UnauthorizedException;
import com.example.ittalentsfinalprojectairbnb.model.dto.UserGetByIdDTO;
import com.example.ittalentsfinalprojectairbnb.model.dto.UserLogInDTO;
import com.example.ittalentsfinalprojectairbnb.model.dto.UserResponseDTO;
import com.example.ittalentsfinalprojectairbnb.model.dto.UserRegisterDTO;
import com.example.ittalentsfinalprojectairbnb.model.entities.User;
import com.example.ittalentsfinalprojectairbnb.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;

@RestController
public class UserController {

    public static final String LOGGED = "logged";
    public static final String LOGGED_FROM = "logged_from";
    public static final String USER_ID = "user_id";

    @Autowired
    private UserService service;
    @Autowired
    private ModelMapper mapper;


    @PostMapping("/login")
    public UserResponseDTO login(@RequestBody UserLogInDTO user, HttpSession session, HttpServletRequest request) {

        UserResponseDTO u = service.login(user.getEmail(), user.getPassword());
        session.setAttribute(LOGGED, true);
        session.setAttribute(LOGGED_FROM, request.getRemoteAddr());
        session.setAttribute(USER_ID, u.getId());

        return u;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserRegisterDTO user) {
//
        UserResponseDTO u = service.register(user.getEmail(), user.getPassword(), user.getConfirmedPassword(),
                user.getFirstName(), user.getLastName(), user.getGender(), user.getDateOfBirth(), user.getPhoneNumber(), user.isHost());


        return ResponseEntity.ok(u);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return new ResponseEntity<>("Logged out successfully!", HttpStatus.OK);
    }

    @PostMapping("/add_photo")
    public ResponseEntity<UserResponseDTO> addPhoto(@RequestBody User user, HttpSession session, HttpServletRequest request) {
        validateLogin(session, request);
        user = service.addPhoto(user);

        UserResponseDTO dto = mapper.map(user, UserResponseDTO.class);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/delete_user/{id}")
    public ResponseEntity<UserResponseDTO> deleteUser(@PathVariable("id") int id, HttpSession session, HttpServletRequest request) {
        validateLogin(session, request);
        User user = service.deleteById(id);
        session.invalidate();
        UserResponseDTO dto = mapper.map(user, UserResponseDTO.class);

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/delete_photo/{id}")
    public ResponseEntity<UserResponseDTO> deletePhoto(@PathVariable("id") int id, HttpSession session, HttpServletRequest request) {
        validateLogin(session, request);
        User user = service.deletePhotoById(id);
        UserResponseDTO dto = mapper.map(user, UserResponseDTO.class);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/get_user/{id}")
    public ResponseEntity<UserGetByIdDTO> getById(@PathVariable("id") int id, HttpSession session, HttpServletRequest request) {
        validateLogin(session, request);
        User user = service.getUserById(id);
        UserGetByIdDTO dto = mapper.map(user, UserGetByIdDTO.class);

        return ResponseEntity.ok(dto);
    }

    private void validateLogin(HttpSession session, HttpServletRequest request) {
        if (session.isNew() ||
                (!(Boolean) session.getAttribute(LOGGED)) ||
                (!request.getRemoteAddr().equals(session.getAttribute(LOGGED_FROM)))) {
            throw new UnauthorizedException("You have to login!");
        }
    }
}
