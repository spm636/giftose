package com.ecommerce.library.service;

import com.ecommerce.library.dto.ReviewDto;
import com.ecommerce.library.model.Review;

import java.util.List;

public interface ReviewService {
    Review save(ReviewDto reviewDto,String email, Long productId);

    List<Review> readReviewByProduct(Long productId);

    Double findRatingByProduct(Long productId);
}
