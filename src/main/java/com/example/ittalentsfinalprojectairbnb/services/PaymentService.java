package com.example.ittalentsfinalprojectairbnb.services;

import com.example.ittalentsfinalprojectairbnb.exceptions.NotFoundException;
import com.example.ittalentsfinalprojectairbnb.exceptions.UnauthorizedException;
import com.example.ittalentsfinalprojectairbnb.model.dto.PaymentResponseDTO;
import com.example.ittalentsfinalprojectairbnb.model.entities.Payment;
import com.example.ittalentsfinalprojectairbnb.model.repositories.PaymentRepository;
import com.example.ittalentsfinalprojectairbnb.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private UserRepository userRepository;

    public Payment addPayment(PaymentResponseDTO paymentDTO, Integer userId) {
        int paymentId = paymentDTO.getId();

        validateUser(userId);

        Payment payment = new Payment();
        payment.setPaymentType(paymentDTO.getPaymentType());
        payment.setDateOfPayment(paymentDTO.getDateOfPayment());
        payment.setTotalPrice(paymentDTO.getTotalPrice());
        payment.setStatus(paymentDTO.getStatus());

        paymentRepository.save(payment);

        return payment;
    }

    public Payment getById(int paymentId, Integer userId) {
        validateUser(userId);
        validatePayment(paymentId);
        Payment payment = paymentRepository.getById(paymentId);
        return payment;
    }

    public Payment confirm(PaymentResponseDTO paymentDTO, Integer userId) {
        int paymentId = paymentDTO.getId();
        validateUser(userId);
        validatePayment(paymentId);

        Payment payment = paymentRepository.getById(paymentId);
        payment.setStatus(paymentDTO.getStatus());
        //todo
        return new Payment();
    }


    private void validatePayment(Integer paymentId) {
        if (!paymentRepository.existsById(paymentId)) {
            throw new NotFoundException("There is no such payment");
        }
    }

    private void validateUser(Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("There is no such user!");
        }

        if (userId == null) {
            throw new UnauthorizedException("You have to be logged in!");
        }
    }
}
