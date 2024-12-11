package com.RestApiExample.demo.services;

import com.RestApiExample.demo.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.RestApiExample.demo.repositories.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    @Autowired
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository=categoryRepository;
    }
    public List<Category> findAllCategories(){
        return  categoryRepository.findAll();
    }
    public Category findById(Long id){
        return categoryRepository.findById(id).orElseThrow(()->new RuntimeException("Category don`t exist"));
    }
    public Category createCategory(Category category){
        return categoryRepository.save(category);
    }
    public Category updateCategory(Category updatedCategory, Long id){
        Category existingCategory = findById(id);
        existingCategory.setName(updatedCategory.getName());
        existingCategory.setDescription(updatedCategory.getDescription());
        return categoryRepository.save(existingCategory);
    }
    public void deleteById(Long id){
        categoryRepository.deleteById(id);
    }
}
