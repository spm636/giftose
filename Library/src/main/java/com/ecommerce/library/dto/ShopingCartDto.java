package com.ecommerce.library.dto;

import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.Product;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class ShopingCartDto {
    private int id;

    private Customer customer;
    private Product product;
    private int quantity;
    private double unitprice;
    private double totelPrice;
    private boolean deleted;

}
