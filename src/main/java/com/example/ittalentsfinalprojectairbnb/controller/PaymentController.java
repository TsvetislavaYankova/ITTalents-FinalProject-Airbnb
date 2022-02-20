package com.example.ittalentsfinalprojectairbnb.controller;

import com.example.ittalentsfinalprojectairbnb.model.dto.PaymentResponseDTO;
import com.example.ittalentsfinalprojectairbnb.model.entities.Payment;
import com.example.ittalentsfinalprojectairbnb.services.PaymentService;
import com.example.ittalentsfinalprojectairbnb.utils.SessionManager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class PaymentController {

    @Autowired
    private PaymentService service;
    @Autowired
    private ModelMapper mapper;


    @PostMapping("/add_payment")
    public ResponseEntity<PaymentResponseDTO> addPayment(@RequestBody PaymentResponseDTO paymentDTO, HttpSession session) {
        Payment payment = service.addPayment(mapper.map(paymentDTO, Payment.class), (Integer) session.getAttribute(SessionManager.USER_ID));
        PaymentResponseDTO dto = mapper.map(payment, PaymentResponseDTO.class);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("get_payment/{id}")
    public ResponseEntity<PaymentResponseDTO> getById(@PathVariable("id") int paymentId, HttpSession session) {
        Payment payment = service.getById(paymentId, (Integer) session.getAttribute(SessionManager.USER_ID));
        PaymentResponseDTO dto = mapper.map(payment, PaymentResponseDTO.class);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("get_payment_status/{id}")
    public ResponseEntity<PaymentResponseDTO> getStatus(@PathVariable("id") int paymentId, HttpSession session) {
        Payment payment = service.getById(paymentId, (Integer) session.getAttribute(SessionManager.USER_ID));
        PaymentResponseDTO dto = mapper.map(payment, PaymentResponseDTO.class);

        return ResponseEntity.ok(dto);
    }

    @PutMapping("/confirm_payment")
    public ResponseEntity<PaymentResponseDTO> confirm(@RequestBody PaymentResponseDTO paymentDTO, HttpSession session) {
        Payment payment = service.confirm(paymentDTO, (Integer) session.getAttribute(SessionManager.USER_ID));
        PaymentResponseDTO dto = mapper.map(payment, PaymentResponseDTO.class);

        return ResponseEntity.ok(dto);
    }
}
