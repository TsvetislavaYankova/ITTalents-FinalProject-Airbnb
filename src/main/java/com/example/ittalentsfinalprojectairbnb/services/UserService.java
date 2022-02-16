package com.example.ittalentsfinalprojectairbnb.services;

import com.example.ittalentsfinalprojectairbnb.exceptions.BadRequestException;
import com.example.ittalentsfinalprojectairbnb.exceptions.NotFoundException;
import com.example.ittalentsfinalprojectairbnb.exceptions.UnauthorizedException;
import com.example.ittalentsfinalprojectairbnb.model.entities.User;
import com.example.ittalentsfinalprojectairbnb.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public User login(String email, String password) {
        if (email.isBlank() || email == null) {
            throw new BadRequestException("Email is a mandatory field!");
        }
        if (password.isBlank() || password == null) {
            throw new BadRequestException("Password is a mandatory field!");
        }
        User user = repository.findByEmailAndPassword(email, password);
        if (user == null) {
            throw new UnauthorizedException("Wrong credentials! Access denied!");
        }

        return user;
    }

    public User register(String email, String password, String confirmedPassword,
                         String firstName, String lastName, char gender,
                         LocalDateTime dateOfBirth, String phoneNumber, boolean isHost) {
        if (email.isBlank() || email == null) {
            throw new BadRequestException("Email is a mandatory field!");
        }

//        if (email.matches("^ [a-zA-Z0-9+_.-]+@ [a-zA-Z0-9.-]+$")) {
//           throw new BadRequestException("You must enter valid email address!");
//        }
//        if (!email.matches("^ [a-zA-Z0-9+_.-]+@ [a-zA-Z0-9.-]+$")) {
//            throw new BadRequestException("You must enter valid email address!");
//        }

        if(password == null || password.isBlank()){
            throw new BadRequestException("Password is a mandatory field!");
        }
//       if(!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\\\S+$).{8,20}$")){
//            throw new BadRequestException("Password should be: at least 8 symbols long. " +
//                    "Contain at least one digit. " +
//                    "Contain at least one upper case character. " +
//                    "No spaces are allowed");
//       }
        if(!password.equals(confirmedPassword)){
            throw new BadRequestException("Passwords mismatch!");
        }
//        if(!phoneNumber.matches("^\\\\d{10}$")){
//            throw new BadRequestException("Phone number should be 10 digits long. " );
//        }
        if(repository.findByEmail(email) != null){
            throw new BadRequestException("User already exists!");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setDateOfBirth(dateOfBirth);
        user.setGender(gender);
        user.setHost(isHost);
        user.setPhoneNumber(phoneNumber);

        repository.save(user);

        return user;
    }

    //todo
    public User addPhoto(User user) {

        return new User();
    }

    //todo
    public User deleteById(int id) {
        return new User();
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
