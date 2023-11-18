package com.ecommerce.library.serviceImpliment;

import com.ecommerce.library.dto.ReviewDto;
import com.ecommerce.library.model.Product;
import com.ecommerce.library.model.Review;
import com.ecommerce.library.repository.ReviewRepository;
import com.ecommerce.library.service.CustomerService;
import com.ecommerce.library.service.ProductService;
import com.ecommerce.library.service.ReviewService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private ReviewRepository repository;
    private ProductService productService;
    private CustomerService customerService;

    public ReviewServiceImpl(ReviewRepository repository, ProductService productService, CustomerService customerService) {
        this.repository = repository;
        this.productService = productService;
        this.customerService = customerService;
    }

    @Override
    public Review save(ReviewDto reviewDto,String email, Long productId) {
        Product product=productService.getById(productId);
        Review review=new Review();
        review.setComment(reviewDto.getComment());
        review.setReviewDate(new Date());
        review.setRating(reviewDto.getRating());
        review.setCustomer(customerService.findByEmail(email));
        review.setProduct(product);
        return repository.save(review);
    }

    @Override
    public List<Review> readReviewByProduct(Long productId) {

        return repository.readReviewByProduct(productId);
    }

    @Override
    public Double findRatingByProduct(Long productId) {

        Object average=repository.findRatingByProduct(productId);
        Double avgrating= (Double) average;
        return avgrating;
    }
}
