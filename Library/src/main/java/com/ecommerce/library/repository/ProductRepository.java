package com.ecommerce.library.repository;

import com.ecommerce.library.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query(value = "select * from products where is_activated = true ", nativeQuery = true)
    List<Product> findAllByActivatedTrue();

    Product findByName(String name);
    @Query("select p from Product p  join Category c ON c.id = p.category.id" +
            " where p.category.name = ?1")
    List<Product> findAllByCategory(String category);

    @Query(value="select * from products p inner join categories c on p.category_id=c.category_id where p.is_activated=true and c.is_activated=true", nativeQuery = true)
    List<Product> findProductByCategoryAndIs_activated();

    @Query(value = "SELECT p.*,c.category_id as cat_id FROM products p JOIN categories c ON p.category_id=c.category_id WHERE p.is_deleted=FALSE AND c.is_deleted=FALSE ",nativeQuery = true)
    List<Product> listViewProducts();

    @Query(value = "SELECT p.*,c.category_id as cat_id FROM products p JOIN categories c ON p.category_id=c.category_id WHERE p.is_Deleted=FALSE AND c.is_deleted=FALSE AND (LOWER(p.name) LIKE %:keyword% OR LOWER(p.product_color) LIKE %:keyword% OR LOWER(c.name) LIKE %:keyword%)",nativeQuery = true)
    List<Product> listViewProductsUserSide(String keyword);

    @Query("select p from Product p JOIN Category c ON p.category.id=c.id")
    Page<Product> findAllProductPagable(Pageable pageable);

}
