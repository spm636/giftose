package com.ecommerce.library.dto;

import com.ecommerce.library.model.Category;
import com.ecommerce.library.model.Product;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class OfferDto {
    private Long id;
    private String offerName;
    private String descriprion;
    private int offerPercentage;
    private String offerType;
    private Category category;
    private boolean active;
    private Product product;
}
