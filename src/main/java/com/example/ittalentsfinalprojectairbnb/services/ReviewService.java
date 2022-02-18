package com.example.ittalentsfinalprojectairbnb.services;

import com.example.ittalentsfinalprojectairbnb.exceptions.NotFoundException;
import com.example.ittalentsfinalprojectairbnb.exceptions.UnauthorizedException;
import com.example.ittalentsfinalprojectairbnb.model.dto.ReviewResponseDTO;
import com.example.ittalentsfinalprojectairbnb.model.entities.Review;
import com.example.ittalentsfinalprojectairbnb.model.repositories.PropertyRepository;
import com.example.ittalentsfinalprojectairbnb.model.repositories.ReviewRepository;
import com.example.ittalentsfinalprojectairbnb.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PropertyRepository propertyRepository;

    public Review addReview(ReviewResponseDTO reviewDTO, Integer userId) {
        int propertyId = reviewDTO.getPropertyId();

        validateUser(userId);
        validateProperty(propertyId);

        Review review = new Review();
        review.setUserId(userId);
        review.setPropertyId(propertyId);
        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());

        reviewRepository.save(review);

        return review;
    }

    public void deleteReview(int reviewId, Integer userId) {
        validateUser(userId);
        validateReview(reviewId);
        //validateProperty();

        reviewRepository.deleteById(reviewId);
    }

    public Review getById(int reviewId, Integer userId) {
        validateUser(userId);
        validateReview(reviewId);

        Review review = reviewRepository.getById(reviewId);

        return review;
    }

    private void validateUser(Integer userId) {
        if (userId == null) {
            throw new UnauthorizedException("To add a review you have to be logged in!");
        }

        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("There is no such user!");
        }
    }

    private void validateProperty(int propertyId) {
        if (!propertyRepository.existsById(propertyId)) {
            throw new NotFoundException("This property doesn't exist!");
        }
    }

    private void validateReview(int reviewId) {
        if (!reviewRepository.existsById(reviewId)) {
            throw new NotFoundException("This review doesn't exist!");
        }
    }
}
