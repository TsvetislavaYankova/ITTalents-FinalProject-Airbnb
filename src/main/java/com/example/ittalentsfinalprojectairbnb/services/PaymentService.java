package com.example.ittalentsfinalprojectairbnb.services;

import com.example.ittalentsfinalprojectairbnb.exceptions.NotFoundException;
import com.example.ittalentsfinalprojectairbnb.model.dto.PaymentResponseDTO;
import com.example.ittalentsfinalprojectairbnb.model.entities.Payment;
import com.example.ittalentsfinalprojectairbnb.model.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment addPayment(PaymentResponseDTO paymentDTO) {
        Payment payment = new Payment();

        payment.setDateOfPayment(paymentDTO.getDateOfPayment());
        payment.setTotalPrice(paymentDTO.getTotalPrice());
        payment.setPaymentType(paymentDTO.getPaymentType());
        payment.setStatus(paymentDTO.getStatus());

        paymentRepository.save(payment);

        return payment;
    }

    public Payment getById(int paymentId) {
        return paymentRepository.findById(paymentId).orElseThrow(() -> new NotFoundException("There is no such payment!"));
    }

    public Payment confirm(PaymentResponseDTO paymentDTO) {
        int paymentId = paymentDTO.getId();

        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new NotFoundException("There is no such payment!"));
        payment.setStatus(paymentDTO.getStatus());

        paymentRepository.save(payment);

        return payment;
    }
}
