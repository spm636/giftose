package com.ecommerce.library.dto;

import com.ecommerce.library.model.Category;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@ToString
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private int currentQuantity;
    private double costPrice;
    private double salePrice;
private List<MultipartFile> imagesUrl;
    private Category category;
    private boolean activated;
    private boolean deleted;
    private List<String> image;
    private String productColor;



}
