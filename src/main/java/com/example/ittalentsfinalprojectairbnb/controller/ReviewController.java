package com.example.ittalentsfinalprojectairbnb.controller;

import com.example.ittalentsfinalprojectairbnb.model.dto.AddReviewDTO;
import com.example.ittalentsfinalprojectairbnb.model.dto.ReviewResponseDTO;
import com.example.ittalentsfinalprojectairbnb.model.entities.Review;
import com.example.ittalentsfinalprojectairbnb.services.ReviewService;
import com.example.ittalentsfinalprojectairbnb.utils.SessionManager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ReviewController {

    @Autowired
    private ReviewService service;
    @Autowired
    private ModelMapper mapper;

    @PostMapping("/add/review")
    public ResponseEntity<ReviewResponseDTO> addReview(@RequestBody AddReviewDTO reviewDTO, HttpServletRequest request) {
        SessionManager.validateLogin(request);

        Review review = service.addReview(reviewDTO, (Integer) request.getSession().getAttribute(SessionManager.USER_ID));
        ReviewResponseDTO dto = mapper.map(review, ReviewResponseDTO.class);

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/delete/review/{id}")
    public ResponseEntity<ReviewResponseDTO> deleteReview(@PathVariable("id") int reviewId, HttpServletRequest request) {
        SessionManager.validateLogin(request);

        service.deleteReview(reviewId, (Integer) request.getSession().getAttribute(SessionManager.USER_ID));
        ReviewResponseDTO dto = new ReviewResponseDTO();

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/get/review/{id}")
    public ResponseEntity<ReviewResponseDTO> geById(@PathVariable("id") int reviewId, HttpServletRequest request) {
        SessionManager.validateLogin(request);

        Review review = service.getById(reviewId);
        ReviewResponseDTO dto = mapper.map(review, ReviewResponseDTO.class);

        return ResponseEntity.ok(dto);
    }

    @PutMapping("/edit_review")
    public ResponseEntity<ReviewResponseDTO> edit(ReviewResponseDTO reviewDTO, HttpServletRequest request) {
        SessionManager.validateLogin(request);

        Review review = service.edit(reviewDTO);
        ReviewResponseDTO dto = mapper.map(review, ReviewResponseDTO.class);

        return ResponseEntity.ok(dto);
    }
}
