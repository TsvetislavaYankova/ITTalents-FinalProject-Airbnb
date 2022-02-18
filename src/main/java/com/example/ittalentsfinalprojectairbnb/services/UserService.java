package com.example.ittalentsfinalprojectairbnb.services;

import com.example.ittalentsfinalprojectairbnb.exceptions.BadRequestException;
import com.example.ittalentsfinalprojectairbnb.exceptions.NotFoundException;
import com.example.ittalentsfinalprojectairbnb.exceptions.UnauthorizedException;
import com.example.ittalentsfinalprojectairbnb.model.dto.UserResponseDTO;
import com.example.ittalentsfinalprojectairbnb.model.entities.User;
import com.example.ittalentsfinalprojectairbnb.model.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private ModelMapper mapper;


    public UserResponseDTO login(String email, String password) {

        if (email.isBlank() || email == null) {
            throw new BadRequestException("Email is a mandatory field!");
        }
        if (password.isBlank() || password == null) {
            throw new BadRequestException("Password is a mandatory field!");
        }
        User user = repository.findByEmail(email);

        if (!BCrypt.checkpw(password, user.getPassword())) {
            throw new UnauthorizedException("Wrong credentials! Access denied!");
        }
        if (user == null) {
            throw new UnauthorizedException("Wrong credentials! Access denied!");
        }
        UserResponseDTO dto = mapper.map(user, UserResponseDTO.class);
        return dto;
    }

    public UserResponseDTO register(String email, String password, String confirmedPassword,
                                    String firstName, String lastName, char gender,
                                    LocalDateTime dateOfBirth, String phoneNumber, short isHost) {
        if (email.isBlank() || email == null) {
            throw new BadRequestException("Email is a mandatory field!");
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new BadRequestException("You must enter valid email address!");
        }

        if (password == null || password.isBlank()) {
            throw new BadRequestException("Password is a mandatory field!");
        }
        if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*])(?=\\S+$).{8,20}$")) {
            throw new BadRequestException("Password should be: at least 8 symbols long. " +
                    "Contain at least one digit. " +
                    "Contain at least one upper case character. " +
                    "No spaces are allowed");
        }
        if (!password.equals(confirmedPassword)) {
            throw new BadRequestException("Passwords mismatch!");
        }
        if (!phoneNumber.matches("^\\d{10}$")) {
            throw new BadRequestException("Phone number should be 10 digits long. ");
        }
        if (repository.findByEmail(email) != null) {
            throw new BadRequestException("User with provided email address already exists!");
        }
        if (repository.findByPhoneNumber(phoneNumber) != null) {
            throw new BadRequestException("User with provided phone number already exists!");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setDateOfBirth(dateOfBirth);
        user.setGender(gender);
        user.setIsHost(isHost);
        user.setPhoneNumber(phoneNumber);

        repository.save(user);
        UserResponseDTO dto = mapper.map(user, UserResponseDTO.class);
        return dto;
    }

    //todo
    public User addPhoto(User user) {

        return new User();
    }

    public void deleteById(int id) {
        Optional<User> opt = repository.findById(id);
        if (opt.isPresent()) {
            repository.delete(opt.get());
        } else {
            throw new NotFoundException("User not found");
        }
    }

    public User deletePhotoById(int id) {
        return new User();
    }

    public User getUserById(int id) {
        Optional<User> opt = repository.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        } else {
            throw new NotFoundException("User not found");
        }
    }
}
