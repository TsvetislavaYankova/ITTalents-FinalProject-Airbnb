package com.example.ittalentsfinalprojectairbnb.services;

import com.example.ittalentsfinalprojectairbnb.exceptions.NotFoundException;
import com.example.ittalentsfinalprojectairbnb.model.dto.ReviewResponseDTO;
import com.example.ittalentsfinalprojectairbnb.model.entities.Review;
import com.example.ittalentsfinalprojectairbnb.model.repositories.PropertyRepository;
import com.example.ittalentsfinalprojectairbnb.model.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private PropertyRepository propertyRepository;

    public Review addReview(ReviewResponseDTO reviewDTO, Integer userId) {
        int propertyId = reviewDTO.getPropertyId();

        propertyRepository.findById(propertyId).orElseThrow(() -> new NotFoundException("There is no such property"));

        Review review = new Review();
        review.setUserId(userId);
        review.setPropertyId(propertyId);
        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());

        reviewRepository.save(review);

        return review;
    }

    public void deleteReview(int reviewId) {
        reviewRepository.findById(reviewId).orElseThrow(() -> new NotFoundException("There is no such Review!"));

        reviewRepository.deleteById(reviewId);
    }

    public Review getById(int reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(() -> new NotFoundException("There is no such review"));
    }

    public Review edit(ReviewResponseDTO reviewDTO) {
        int reviewId = reviewDTO.getId();
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new NotFoundException("There is no such Review!"));

        Double rating = reviewDTO.getRating();
        String comment = reviewDTO.getComment();

        if(rating!=null){
            review.setRating(rating);
        }

        if(comment != null){
            review.setComment(comment);
        }

        reviewRepository.save(review);

        return review;
    }
}