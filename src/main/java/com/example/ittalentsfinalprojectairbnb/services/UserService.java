package com.example.ittalentsfinalprojectairbnb.services;

import com.example.ittalentsfinalprojectairbnb.exceptions.BadRequestException;
import com.example.ittalentsfinalprojectairbnb.exceptions.NotFoundException;
import com.example.ittalentsfinalprojectairbnb.exceptions.UnauthorizedException;
import com.example.ittalentsfinalprojectairbnb.model.dto.UserEditDTO;
import com.example.ittalentsfinalprojectairbnb.model.dto.UserGetByIdDTO;
import com.example.ittalentsfinalprojectairbnb.model.dto.UserResponseDTO;
import com.example.ittalentsfinalprojectairbnb.model.entities.User;
import com.example.ittalentsfinalprojectairbnb.model.repositories.UserRepository;
import com.example.ittalentsfinalprojectairbnb.utils.SessionManager;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private ModelMapper mapper;


    public UserResponseDTO login(String email, String password) {

        validateEmail(email);

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

        validateEmail(email);

        if (password == null || password.isBlank()) {
            throw new BadRequestException("Password is a mandatory field!");
        }
        validatePassword(password);
        if (!password.equals(confirmedPassword)) {
            throw new BadRequestException("Passwords mismatch!");
        }
        validatePhoneNumber(phoneNumber);
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

    public void deleteById(int id) {
        Optional<User> opt = repository.findById(id);
        if (opt.isPresent()) {
            repository.delete(opt.get());
        } else {
            throw new NotFoundException("User not found");
        }
    }

    public User getUserById(int id) {
        Optional<User> opt = repository.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        } else {
            throw new NotFoundException("User not found");
        }
    }

    public User edit(UserEditDTO userDTO) {
        int id = userDTO.getId();
        String firstName = userDTO.getFirstName();
        String lastName = userDTO.getLastName();
        char gender = userDTO.getGender();
        String email = userDTO.getEmail();
        LocalDateTime dateOfBirth = userDTO.getDateOfBirth();
        String phoneNumber = userDTO.getPhoneNumber();
        short isHost = userDTO.getIsHost();

        User user = repository.findById(id).orElseThrow(() -> new NotFoundException("There is no such user!"));

        if (!firstName.isBlank() && !firstName.equals(user.getFirstName())) {
            user.setFirstName(firstName);
        }

        if (!firstName.isBlank() && !firstName.equals(user.getLastName())) {
            user.setLastName(lastName);
        }

        if (gender != user.getGender()) {
            user.setGender(gender);
        }

        if (!email.isBlank() && !email.equals(user.getEmail())) {
            validateEmail(email);
            user.setEmail(email);
        }

        if (dateOfBirth != null && !dateOfBirth.equals(user.getDateOfBirth())) {
            user.setDateOfBirth(dateOfBirth);
        }

        if (phoneNumber.isBlank() && !phoneNumber.equals(user.getPhoneNumber())) {
            validatePhoneNumber(phoneNumber);
            user.setPhoneNumber(phoneNumber);
        }

        if (isHost != user.getIsHost()) {
            user.setIsHost(isHost);
        }

        repository.save(user);

        return user;
    }


    public User changePassword(UserEditDTO userDTO) {
        int id = userDTO.getId();

        User user = repository.findById(id).orElseThrow(() -> new NotFoundException("There is no such user!"));
        String password = userDTO.getPassword();
        String confirmedPassword = userDTO.getConfirmedPassword();

        if (!password.isBlank() && !confirmedPassword.isBlank()) {
            validatePassword(password);
            if (!password.equals(confirmedPassword)) {
                if (!BCrypt.checkpw(password, user.getPassword())) {
                    user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
                } else {
                    throw new BadRequestException("The new password is the same as the old password!");
                }
            } else {
                throw new BadRequestException("The confirmed password doesn't match the new password!");
            }
        } else {
            throw new BadRequestException("You must enter a new password!");
        }

        repository.save(user);
        return user;
    }

    @SneakyThrows
    public String uploadPhoto(MultipartFile file, int loggedUserId) {

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String name = System.nanoTime() + "." + extension;
        Files.copy(file.getInputStream(), new File("uploads" + File.separator + name).toPath());
        User u = getUserById(loggedUserId);
        u.setPhotoUrl(name);
        repository.save(u);
        return name;
    }

    public void deletePhoto(int id) {
        Optional<User> opt = repository.findById(id);
        if (opt.isPresent()) {
            opt.get().setPhotoUrl(null);
        } else {
            throw new NotFoundException("User not found! Photo could not be deleted!");
        }
    }

    private void validatePhoneNumber(String phoneNumber) {
        if (!phoneNumber.matches("^\\d{10}$")) {
            throw new BadRequestException("Phone number should be 10 digits long.");
        }
    }

    private void validateEmail(String email) {
        if (email.isBlank()) {
            throw new BadRequestException("Email is a mandatory field!");
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new BadRequestException("You must enter valid email address!");
        }
    }

    private void validatePassword(String password) {
        if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*])(?=\\S+$).{8,20}$")) {
            throw new BadRequestException("Password should be: at least 8 symbols long. " +
                    "Contain at least one digit. " +
                    "Contain at least one upper case character. " +
                    "No spaces are allowed");
        }
    }


}
