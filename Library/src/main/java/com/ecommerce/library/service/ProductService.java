package com.ecommerce.library.service;

import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAllProduct();

    List<Product> products();

    List<ProductDto> allProduct();

    void save(ProductDto product);

    void update(ProductDto productDto);

    void enableById(Long id);

    void deleteById(Long id);

    Product getById(Long id);

    Product findById(Long id);



    List<ProductDto> randomProduct();

    Page<ProductDto> searchProducts(int pageNo, String keyword);

    Page<Product> getAllProducts(int pageNo);

    Page<ProductDto> getAllProductsForCustomer(int pageNo);


    List<Product> findAllByCategory(String category);


    List<ProductDto> filterHighProducts();

    List<ProductDto> filterLowerProducts();

    List<Product> listViewProducts();

    List<ProductDto> findByCategoryId(Long id);

    List<Product> searchProducts(String keyword);
    Product findByName(String name);
}
