package com.example.ittalentsfinalprojectairbnb.controller;

import com.example.ittalentsfinalprojectairbnb.model.dto.*;
import com.example.ittalentsfinalprojectairbnb.model.entities.User;
import com.example.ittalentsfinalprojectairbnb.services.EmailService;
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
import javax.servlet.http.HttpSession;


@RestController
public class UserController {

    @Autowired
    private UserService service;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private EmailService emailService;

    @PostMapping("/login")
    public UserResponseDTO login(@RequestBody UserLogInDTO userDTO, HttpServletRequest request) {

        UserResponseDTO user = service.login(userDTO.getEmail(), userDTO.getPassword());
        request.getSession().setAttribute(SessionManager.LOGGED, true);
        request.getSession().setAttribute(SessionManager.LOGGED_FROM, request.getRemoteAddr());
        request.getSession().setAttribute(SessionManager.USER_ID, user.getId());

        return user;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserRegisterDTO userDTO) {

        UserResponseDTO user = service.register(userDTO.getEmail(), userDTO.getPassword(), userDTO.getConfirmedPassword(),
                userDTO.getFirstName(), userDTO.getLastName(), userDTO.getGender(), userDTO.getDateOfBirth(), userDTO.getPhoneNumber(), userDTO.getIsHost());

        return ResponseEntity.ok(user);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return new ResponseEntity<>("Logged out successfully!", HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserGetByIdDTO> getById(@PathVariable("id") int id) {

        User user = service.getUserById(id);
        UserGetByIdDTO dto = mapper.map(user, UserGetByIdDTO.class);

        return ResponseEntity.ok(dto);
    }

    @PutMapping("/edit")
    public ResponseEntity<UserEditDTO> edit(@RequestBody UserEditDTO userDTO, HttpServletRequest request) {
        SessionManager.validateLogin(request);
        int userId = (Integer) request.getSession().getAttribute(SessionManager.USER_ID);
        User user = service.edit(userDTO, userId);
        UserEditDTO dto = mapper.map(user, UserEditDTO.class);

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(HttpServletRequest request) {
        SessionManager.validateLogin(request);
        int userId = (Integer) request.getSession().getAttribute(SessionManager.USER_ID);
        service.deleteById(userId);
        request.getSession().invalidate();

        return new ResponseEntity<>("User deletion successful!", HttpStatus.OK);
    }

    @PutMapping("/change/password")
    public ResponseEntity<UserResponseDTO> changePassword(@RequestBody UserEditDTO userDTO, HttpServletRequest request) {
        SessionManager.validateLogin(request);
       // emailService.sendEmail(userDTO.getEmail(), "password", "changed password");
        int userId = (Integer) request.getSession().getAttribute(SessionManager.USER_ID);
        User user = service.changePassword(userDTO, userId);
        UserResponseDTO dto = mapper.map(user, UserResponseDTO.class);

        return ResponseEntity.ok(dto);
    }

    @PutMapping("/forgot/password")
    public ResponseEntity<UserResponseDTO> forgotPassword(@RequestBody UserEditDTO userDTO, HttpServletRequest request) {
        SessionManager.validateLogin(request);
        int userId = (Integer) request.getSession().getAttribute(SessionManager.USER_ID);
        User user = service.changePassword(userDTO, userId);
        UserResponseDTO dto = mapper.map(user, UserResponseDTO.class);

        return ResponseEntity.ok(dto);
    }

    @SneakyThrows
    @PostMapping("/upload/photo")
    public String uploadProfileImage(@RequestParam(name = "file") MultipartFile file, HttpServletRequest request) {
        SessionManager.validateLogin(request);
        int loggedUserId = (int) request.getSession().getAttribute(SessionManager.USER_ID);
        return service.uploadPhoto(file, loggedUserId);
    }

    @DeleteMapping("/delete/photo")
    public ResponseEntity<String> deletePhoto(HttpServletRequest request) {
        SessionManager.validateLogin(request);
        int loggedUserId = (int) request.getSession().getAttribute(SessionManager.USER_ID);
        service.deletePhoto(loggedUserId);

        return new ResponseEntity<>("Photo deletion successful!", HttpStatus.OK);
    }

    @PutMapping("/edit/photo")
    public String editPhoto(@RequestParam(name = "file") MultipartFile file, HttpServletRequest request) {
        SessionManager.validateLogin(request);
        int id = (int) request.getSession().getAttribute(SessionManager.USER_ID);

        return service.uploadPhoto(file, id);
    }
}