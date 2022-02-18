package com.example.ittalentsfinalprojectairbnb.controller;

import com.example.ittalentsfinalprojectairbnb.model.dto.ReviewResponseDTO;
import com.example.ittalentsfinalprojectairbnb.model.entities.Review;
import com.example.ittalentsfinalprojectairbnb.services.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
public class ReviewController {

    @Autowired
    private ReviewService service;
    @Autowired
    private ModelMapper mapper;

    @PostMapping("/add_review")
    public ResponseEntity<ReviewResponseDTO> addReview(@RequestBody ReviewResponseDTO reviewDTO, HttpSession session) {
        Review review = service.addReview(reviewDTO, (Integer) session.getAttribute(UserController.USER_ID));
        ReviewResponseDTO dto = mapper.map(review, ReviewResponseDTO.class);

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/delete_review/{id}")
    public ResponseEntity<ReviewResponseDTO> deleteReview(@PathVariable("id") int reviewId, HttpSession session) {
        service.deleteReview(reviewId, (Integer) session.getAttribute(UserController.USER_ID));
        ReviewResponseDTO dto = new ReviewResponseDTO();

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/get_review/{id}")
    public ResponseEntity<ReviewResponseDTO> geById(@PathVariable("id") int reviewId, HttpSession session) {
        Review review = service.getById(reviewId,(Integer) session.getAttribute(UserController.USER_ID));
        ReviewResponseDTO dto = mapper.map(review,ReviewResponseDTO.class);

        return ResponseEntity.ok(dto);
    }

}
