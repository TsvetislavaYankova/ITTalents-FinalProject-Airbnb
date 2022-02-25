package com.example.ittalentsfinalprojectairbnb.services;

import com.example.ittalentsfinalprojectairbnb.exceptions.BadRequestException;
import com.example.ittalentsfinalprojectairbnb.exceptions.NotFoundException;
import com.example.ittalentsfinalprojectairbnb.exceptions.UnauthorizedException;
import com.example.ittalentsfinalprojectairbnb.model.dto.AddReviewDTO;
import com.example.ittalentsfinalprojectairbnb.model.dto.ReviewResponseDTO;
import com.example.ittalentsfinalprojectairbnb.model.entities.Property;
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

    public Review addReview(AddReviewDTO reviewDTO, int userId) {
        int propertyId = reviewDTO.getPropertyId();

        propertyRepository.findById(propertyId).orElseThrow(() -> new NotFoundException("There is no such property"));
        
        Review review = new Review();
        review.setUserId(userId);
        review.setPropertyId(propertyId);

        if (reviewDTO.getRating() > 5 || reviewDTO.getRating() < 1) {
            throw new BadRequestException("Rating must be between 1 and 5!");
        }

        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());

        reviewRepository.save(review);

        return review;
    }

    public void deleteReview(int reviewId, int userId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new NotFoundException("There is no such review!"));
        Property property = propertyRepository.findById(review.getPropertyId())
                .orElseThrow(() -> new NotFoundException("There is no such property!"));

        if (userId != review.getUserId()) {
            if (property.getHost().getId() != userId) {
                throw new BadRequestException("You can't delete this review!");
            }
        }

        reviewRepository.deleteById(reviewId);
    }

    public Review getById(int reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(() -> new NotFoundException("There is no such review"));
    }

    public Review edit(ReviewResponseDTO reviewDTO, int userId) {
        int reviewId = reviewDTO.getId();
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new NotFoundException("There is no such Review!"));

        if (review.getUserId() != userId) {
            throw new UnauthorizedException("You can't edit this review!");
        }


        Double rating = reviewDTO.getRating();
        {
            String comment = reviewDTO.getComment();

            if (rating != review.getRating()) {
                review.setRating(rating);
            }

            if (!comment.isBlank()) {
                review.setComment(comment);
            }

            reviewRepository.save(review);

            return review;
        }
    }
}