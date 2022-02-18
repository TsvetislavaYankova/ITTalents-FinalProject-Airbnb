package com.example.ittalentsfinalprojectairbnb.controller;

import com.example.ittalentsfinalprojectairbnb.model.dto.AddReviewDTO;
import com.example.ittalentsfinalprojectairbnb.model.dto.DeleteReviewDTO;
import com.example.ittalentsfinalprojectairbnb.model.dto.ReviewResponseDTO;
import com.example.ittalentsfinalprojectairbnb.model.entities.Property;
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
    public ResponseEntity<AddReviewDTO> addReview(@RequestBody Property property, HttpSession session) {
        int id = property.getId();

        Review review = service.addReview(id, (Integer) session.getAttribute(UserController.USER_ID));
        AddReviewDTO dto = mapper.map(review, AddReviewDTO.class);

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/delete_review/{id}")
    public ResponseEntity<DeleteReviewDTO> deleteReview(@PathVariable("id") int reviewId, HttpSession session) {
        service.deleteReview(reviewId, (Integer) session.getAttribute(UserController.USER_ID));

        DeleteReviewDTO dto = new DeleteReviewDTO();

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/get_review/{id}")
    public ResponseEntity<ReviewResponseDTO> geById(@PathVariable("id") int reviewId, HttpSession session) {
        Review review = service.getById(reviewId,(Integer) session.getAttribute(UserController.USER_ID));

        ReviewResponseDTO dto = mapper.map(review,ReviewResponseDTO.class);

        return ResponseEntity.ok(dto);
    }

}
