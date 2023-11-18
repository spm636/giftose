package com.ecommerce.library.repository;

import com.ecommerce.library.dto.CategoryDto;
import com.ecommerce.library.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    @Query(value = "update Category set name = ?1 where id = ?2")
    Category update(String name, Long id);

    @Query("SELECT c FROM Category c where c.activated=true ")
    List<Category> findAllByActivatedTrue();

   /* @Query(value = "select new com.ecommerce.library.dto.CategoryDto(c.id, c.name, count(p.category.id)) " +
            "from Category c left join Product p on c.id = p.category.id " +
            "where c.activated = true and c.deleted = false " +
            "group by c.id ")
    List<CategoryDto> getCategoriesAndSize(); */
    Category findByName(String name);
}
