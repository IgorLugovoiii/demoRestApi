package com.RestApiExample.demo.services;

import com.RestApiExample.demo.dto.CategoryDto;
import com.RestApiExample.demo.models.Category;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.RestApiExample.demo.repositories.CategoryRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    @Autowired
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository=categoryRepository;
    }
    private CategoryDto convertToCategoryDto(Category category){
        return new CategoryDto(category);
    }

    @Transactional(readOnly = true)
    public List<CategoryDto> findAllCategories(){
            return categoryRepository.findAll().stream()
                    .map(this::convertToCategoryDto)
                    .toList();
    }
    @Transactional(readOnly = true)
    public CategoryDto findById(Long id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        return convertToCategoryDto(category);
    }
    @Transactional
    public CategoryDto createCategory(Category category){
        return convertToCategoryDto(categoryRepository.save(category));
    }
    @Transactional
    public CategoryDto updateCategory(Category updatedCategory, Long id){
            Category existingCategory = categoryRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Category not found"));
            existingCategory.setName(updatedCategory.getName());
            existingCategory.setDescription(updatedCategory.getDescription());
            return convertToCategoryDto(categoryRepository.save(existingCategory));
    }
    @Transactional
    public void deleteById(Long id){
            categoryRepository.deleteById(id);
    }
}
