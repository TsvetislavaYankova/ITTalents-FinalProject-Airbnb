package com.example.ittalentsfinalprojectairbnb.services;

import com.example.ittalentsfinalprojectairbnb.exceptions.NotFoundException;
import com.example.ittalentsfinalprojectairbnb.exceptions.UnauthorizedException;
import com.example.ittalentsfinalprojectairbnb.model.dto.PaymentResponseDTO;
import com.example.ittalentsfinalprojectairbnb.model.entities.Payment;
import com.example.ittalentsfinalprojectairbnb.model.repositories.PaymentRepository;
import com.example.ittalentsfinalprojectairbnb.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private UserRepository userRepository;

    public Payment addPayment(Payment payment, Integer userId) {
        validateUser(userId);

        paymentRepository.save(payment);

        return payment;
    }

    public Payment getById(int paymentId, Integer userId) {
        validateUser(userId);

        return paymentRepository.findById(paymentId).orElseThrow(() -> new NotFoundException("There is no such payment!"));
    }

    public Payment confirm(PaymentResponseDTO paymentDTO, Integer userId) {
        int paymentId = paymentDTO.getId();
        validateUser(userId);

        Payment payment = paymentRepository.findById(paymentId).orElseThrow(()-> new NotFoundException("There is no such payment!"));
        payment.setStatus(paymentDTO.getStatus());
        //todo
        return new Payment();
    }

    private void validateUser(Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("There is no such user!");
        }

        if (userId == null) {//\todo  use validate
            throw new UnauthorizedException("You have to be logged in!");
        }
    }
}
