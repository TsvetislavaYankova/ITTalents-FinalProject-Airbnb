package com.example.ittalentsfinalprojectairbnb.controller;

import com.example.ittalentsfinalprojectairbnb.exceptions.UnauthorizedException;
import com.example.ittalentsfinalprojectairbnb.model.dto.UserGetByIdDTO;
import com.example.ittalentsfinalprojectairbnb.model.dto.UserLogInDTO;
import com.example.ittalentsfinalprojectairbnb.model.dto.UserResponseDTO;
import com.example.ittalentsfinalprojectairbnb.model.dto.UserRegisterDTO;
import com.example.ittalentsfinalprojectairbnb.model.entities.User;
import com.example.ittalentsfinalprojectairbnb.model.repositories.UserRepository;
import com.example.ittalentsfinalprojectairbnb.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


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
    public UserResponseDTO login(@RequestBody UserLogInDTO user, HttpServletRequest request) {

        UserResponseDTO u = service.login(user.getEmail(), user.getPassword());
        request.getSession().setAttribute(LOGGED, true);
        request.getSession().setAttribute(LOGGED_FROM, request.getRemoteAddr());
        request.getSession().setAttribute(USER_ID, u.getId());

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
    public ResponseEntity<UserResponseDTO> addPhoto(@RequestBody User user, HttpServletRequest request) {
        validateLogin(request);
        user = service.addPhoto(user);

        UserResponseDTO dto = mapper.map(user, UserResponseDTO.class);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/delete_user")
    public ResponseEntity<String> deleteUser(HttpServletRequest request) {
        validateLogin(request);
        int userId = (Integer) request.getSession().getAttribute(USER_ID);
        service.deleteById(userId);
        request.getSession().invalidate();

        return new ResponseEntity<>("Deletion successful!",HttpStatus.OK);
    }

    @DeleteMapping("/delete_photo/{id}")
    public ResponseEntity<UserResponseDTO> deletePhoto(@PathVariable("id") int id, HttpServletRequest request) {
        validateLogin(request);
        User user = service.deletePhotoById(id);
        UserResponseDTO dto = mapper.map(user, UserResponseDTO.class);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/get_user/{id}")
    public ResponseEntity<UserGetByIdDTO> getById(@PathVariable("id") int id, HttpServletRequest request) {
        validateLogin(request);
        User user = service.getUserById(id);
        UserGetByIdDTO dto = mapper.map(user, UserGetByIdDTO.class);

        return ResponseEntity.ok(dto);
    }

    //TODO refactor -> remove session from parameters
    public static void validateLogin(HttpServletRequest request) {
        boolean newSession = request.getSession().isNew();
        boolean logged = request.getSession().getAttribute(LOGGED) != null && ((Boolean) request.getSession().getAttribute(LOGGED));
        boolean sameIP = request.getRemoteAddr().equals(request.getSession().getAttribute(LOGGED_FROM));
        if (newSession || !logged || !sameIP) {
            throw new UnauthorizedException("You have to login!");
        }
    }
}