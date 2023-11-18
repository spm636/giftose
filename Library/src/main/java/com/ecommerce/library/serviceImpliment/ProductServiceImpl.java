package com.ecommerce.library.serviceImpliment;

import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.Product;
import com.ecommerce.library.repository.ProductRepository;
import com.ecommerce.library.service.CategoryService;
import com.ecommerce.library.service.ProductService;
import com.ecommerce.library.utils.ImageUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class ProductServiceImpl implements ProductService {


   private final  ProductRepository productRepository;

   private final CategoryService categoryService;

  private final    ImageUpload imageUpload;

@Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService, ImageUpload imageUpload) {
        this.productRepository = productRepository;
    this.categoryService = categoryService;
    this.imageUpload = imageUpload;
    }


    @Override
    public List<Product> findAllProduct() {


    return productRepository.findAll();
    }

    @Override
    public List<Product> products() {


        return productRepository.findAllByActivatedTrue();

    }


    @Override
    public List<ProductDto> allProduct() {

        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos=transferData(products);

        return productDtos;
    }

    @Override
    public void save( ProductDto productDto) {

        Product product = new Product();

           product.setName(productDto.getName());
           product.setDescription(productDto.getDescription());
           product.setCurrentQuantity(productDto.getCurrentQuantity());
           product.setCostPrice(productDto.getCostPrice());
           product.setSalePrice(productDto.getCostPrice());
           product.setCategory(productDto.getCategory());
           product.setProductColor(productDto.getProductColor());
           product.setImagesUrl(imageUpload.uploadToLocalAndReadyImages(productDto.getImagesUrl()));
           product.set_deleted(false);
           product.set_activated(true);
           productRepository.save(product);


    }


    @Override
    public void update(ProductDto productDto) {

        Product product = productRepository.getById(productDto.getId());
        if (imageUpload.uploadToLocalAndReadyImages(productDto.getImagesUrl()).isEmpty()) {
            product.setImagesUrl(product.getImagesUrl());


        }
        else{
product.setImagesUrl(imageUpload.uploadToLocalAndReadyImages(productDto.getImagesUrl()));
        }


        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setCurrentQuantity(productDto.getCurrentQuantity());
        product.setCostPrice(productDto.getCostPrice());
       // product.setSalePrice(productDto.getSalePrice());
        product.setCategory(productDto.getCategory());
        product.setProductColor(productDto.getProductColor());
       // product.setImagesUrl(imageUpload.uploadToLocalAndReadyImages(productDto.getImagesUrl()));
        product.set_deleted(false);
        product.set_activated(true);
         productRepository.save(product);
    }

    @Override
    public void enableById(Long id) {
    Product product=productRepository.getById(id);
    product.set_deleted(false);
    product.set_activated(true);
    productRepository.save(product);

    }

    @Override
    public void deleteById(Long id) {
    Product product=productRepository.getById(id);
    product.set_deleted(true);
    product.set_activated(false);
    productRepository.save(product);

    }

    @Override
    public Product getById(Long id) {
        return productRepository.getById(id);

    }


    @Override
    public Product findById(Long id) {

    return productRepository.getReferenceById(id) ;
    }

    @Override
    public List<ProductDto> randomProduct() {
        return null;
    }

    @Override
    public Page<ProductDto> searchProducts(int pageNo, String keyword) {
        return null;
    }

    @Override
    public Page<Product> getAllProducts(int pageNo) {
        Pageable pageable= PageRequest.of(pageNo,6);
        Page<Product> products=this.productRepository.findAllProductPagable(pageable);
    return products;
    }
    @Override
    public Page<ProductDto> getAllProductsForCustomer(int pageNo) {
        return null;
    }

    @Override
    public List<Product> findAllByCategory(String category) {

    return productRepository.findAllByCategory(category);
    }

    @Override
    public List<ProductDto> filterHighProducts() {

    return null;
    }

    @Override
    public List<ProductDto> filterLowerProducts() {
        return null;
    }

    @Override
    public List<Product> listViewProducts() {

    return productRepository.listViewProducts();
    }

    @Override
    public List<ProductDto> findByCategoryId(Long id) {

    return null;
    }

    @Override
    public List<Product> searchProducts(String keyword) {
    List<Product> products=productRepository.listViewProductsUserSide(keyword);
    return products;
    }
    private List<ProductDto> transferData(List<Product> products) {
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            ProductDto productDto = new ProductDto();
            productDto.setId(product.getId());
            productDto.setName(product.getName());
            productDto.setCurrentQuantity(product.getCurrentQuantity());
            productDto.setCostPrice(product.getCostPrice());
            productDto.setSalePrice(product.getSalePrice());
            productDto.setDescription(product.getDescription());
            List<String> image=product.getImagesUrl();
           // productDto.setImagesUrl(imageUpload.uploadToLocalAndReadyImages((List<MultipartFile>) image));
         productDto.setImage(image);
            productDto.setCategory(product.getCategory());
            productDto.setActivated(product.is_activated());
            productDto.setDeleted(product.is_deleted());
            productDtos.add(productDto);
        }
        return productDtos;
    }

    @Override
    public Product findByName(String name) {
        return productRepository.findByName(name);
    }
}
