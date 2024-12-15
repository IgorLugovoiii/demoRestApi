package com.RestApiExample.demo.services;

import com.RestApiExample.demo.dto.CategoryDto;
import com.RestApiExample.demo.models.Category;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.RestApiExample.demo.repositories.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);
    @Autowired
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository=categoryRepository;
    }
    private CategoryDto convertToCategoryDto(Category category){
        return new CategoryDto(category);
    }
    public List<CategoryDto> findAllCategories(){
        try {
            logger.debug("Attempting to find all categories");
            List<CategoryDto> categoryDtos = categoryRepository.findAll().stream()
                    .map(this::convertToCategoryDto)
                    .toList();
            logger.debug("Found {} categories", categoryDtos.size());
            return categoryDtos;
        }catch (Exception e){
            logger.error("Failed to find categories due to {}", e.getMessage(), e);
            throw new RuntimeException("Unable to find any category", e);
        }
    }
    public CategoryDto findById(Long id){
        logger.debug("Looking for category with id: {}", id);
        try{
        Category category = categoryRepository.findById(id)
                .orElseThrow(()->{
                    logger.warn("Category with id: {} not found", id);
                    return new EntityNotFoundException("Category not found");
                });
        return convertToCategoryDto(category);
        } catch (Exception e){
            logger.error("Failed to find category with id {} due to {}", id, e.getMessage(), e);
            throw new RuntimeException("Unable to find category by id", e);
        }
    }
    public CategoryDto createCategory(Category category){
        try{
            logger.debug("Attempting to create category: {}", category);
        return convertToCategoryDto(categoryRepository.save(category));
        } catch (Exception e){
            logger.error("Failed to create category due to {}", e.getMessage(), e);
            throw new RuntimeException("Unable to create category, please check the input data", e);
        }
    }
    public CategoryDto updateCategory(Category updatedCategory, Long id){
        try {
            logger.debug("Updating category with id {}", id);
            Category existingCategory = categoryRepository.findById(id)
                    .orElseThrow(() -> {
                        logger.warn("Category with id {} not found for update", id);
                        return new EntityNotFoundException("Category not found");
                    });
            existingCategory.setName(updatedCategory.getName());
            existingCategory.setDescription(updatedCategory.getDescription());
            return convertToCategoryDto(categoryRepository.save(existingCategory));
        } catch (Exception e) {
            logger.error("Failed to update category with id {} due to {}", id, e.getMessage(), e);
            throw new RuntimeException("Unable to update category, please check the input data", e);
        }
    }
    public void deleteById(Long id){
        try {
            logger.debug("Attempting to delete category with id {}", id);
            categoryRepository.deleteById(id);
            logger.debug("Category with id {} successfully deleted", id);
        } catch (EntityNotFoundException e) {
            logger.warn("Category with id {} not found for deletion", id);
            throw e;
        } catch (Exception e) {
            logger.error("Failed to delete category with id {} due to {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to delete category, please check the input data", e);
        }
    }
}
