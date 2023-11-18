package com.ecommerce.library.dto;

import com.ecommerce.library.model.Customer;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class ReviewDto {
    private Long id;
    private String comment;
    private Date reviewDate;
    private Customer customer;
    private Double rating;
    private Double avgRating;

}
