package com.ecommerce.library.serviceImpliment;

import com.ecommerce.library.dto.CategoryDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.repository.CategoryRepository;
import com.ecommerce.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    CategoryRepository categoryRepository;
@Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }



    @Override
    public void saveCategory(CategoryDto categoryDto) {
        Category categorySave=new Category();
        categorySave.setActivated(true);
        categorySave.setDeleted(false);
        categorySave.setName(categoryDto.getName());


         categoryRepository.save(categorySave);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public void update(CategoryDto category) {

       Category categoryupdate=categoryRepository.getReferenceById(category.getId());
       categoryupdate.setName(category.getName());
       categoryupdate.setActivated(category.isActivated());
       categoryupdate.setDeleted(category.isDeleted());
       categoryupdate.setDescription(category.getDescription());

       categoryRepository.save(categoryupdate);
    }

    @Override
    public List<Category> findAllByActivatedTrue() {

    return categoryRepository.findAllByActivatedTrue();
    }

    @Override
    public List<Category> findALl() {

    return categoryRepository.findAll();
    }



    @Override
    public void deleteById(Long id) {
        Category category = categoryRepository.getById(id);
        category.setActivated(false);
        category.setDeleted(true);
        categoryRepository.save(category);

    }

    @Override
    public void enableById(Long id) {
        Category category = categoryRepository.getById(id);
        category.setActivated(true);
        category.setDeleted(false);
        categoryRepository.save(category);

    }

    @Override
    public List<CategoryDto> getCategoriesAndSize() {
        return null;
    }

    @Override
    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }
}
