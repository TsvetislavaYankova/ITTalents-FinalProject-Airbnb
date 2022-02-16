package com.example.ittalentsfinalprojectairbnb.services;

import com.example.ittalentsfinalprojectairbnb.exceptions.BadRequestException;
import com.example.ittalentsfinalprojectairbnb.exceptions.UnauthorizedException;
import com.example.ittalentsfinalprojectairbnb.model.entities.User;
import com.example.ittalentsfinalprojectairbnb.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

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

    //todo checks
    public User register(String email, String password, String verifiedPassword) {
        if (email.isBlank() || email == null) {

        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.set_host(false);
        user.setFirst_name("nqkoj");
        user.setLast_name("nikoj");
        //user.setPassword(passwordEncoder.encode(password));//TODO add bcrypt

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
        return new User();
    }
}
