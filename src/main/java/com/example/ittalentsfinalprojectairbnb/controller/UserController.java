package com.example.ittalentsfinalprojectairbnb.controller;

import com.example.ittalentsfinalprojectairbnb.model.dto.UserGetByIdDTO;
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
import java.util.ArrayList;

@RestController("/users")
public class UserController {

    public static final String LOGGED = "logged";
    public static final String LOGGED_FROM = "logged_from";

    @Autowired
    private UserService service;
    @Autowired
    private ModelMapper mapper;

    private ArrayList<User> users = new ArrayList<>();

    @PostMapping("/login")
    public UserResponseDTO login(@RequestBody User user, HttpSession session, HttpServletRequest request) {
        String email = user.getEmail();
        String password = user.getPassword();

        User u = service.login(email, password);
        session.setAttribute(LOGGED, true);
        session.setAttribute(LOGGED_FROM, request.getRemoteAddr());

        UserResponseDTO dto = mapper.map(u, UserResponseDTO.class);

        return dto;
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegisterDTO> register(@RequestBody UserRegisterDTO user) {
        String email = user.getEmail();
        String password = user.getPassword();
        String verifiedPassword = user.getVerifyPassword();

        User u = service.register(email, password, verifiedPassword);
        users.add(u);
        UserRegisterDTO dto = mapper.map(u, UserRegisterDTO.class);

        return ResponseEntity.ok(dto);
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

    @GetMapping("/{id}")
    public ResponseEntity<UserGetByIdDTO> getById(@PathVariable("id") int id, HttpSession session, HttpServletRequest request) {
        validateLogin(session, request);
        User user = service.getUserById(id);
        UserGetByIdDTO dto = mapper.map(user, UserGetByIdDTO.class);

        return ResponseEntity.ok(dto);
    }

    //todo
    private void validateLogin(HttpSession session, HttpServletRequest request) {
        if (session.isNew() ||
                (!(Boolean) session.getAttribute(LOGGED)) ||
                (!request.getRemoteAddr().equals(session.getAttribute(LOGGED_FROM)))) {
            //throw new UnauthorizedException("You have to login!");
        }
    }
}
