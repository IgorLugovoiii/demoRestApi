package com.RestApiExample.demo.services;

import com.RestApiExample.demo.dto.CategoryDto;
import com.RestApiExample.demo.models.Category;
import com.RestApiExample.demo.repositories.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void testFindAllCategories(){
        Category category = new Category();
        category.setName("Test");
        List<Category> categories = List.of(category);
        Mockito.when(categoryRepository.findAll()).thenReturn(categories);

        List<CategoryDto> categoryDtos = categoryService.findAllCategories();

        Assertions.assertEquals(1,categoryDtos.size());
        Assertions.assertEquals("Test",categoryDtos.getFirst().getName());
        Mockito.verify(categoryRepository).findAll();
    }

    @Test
    void testFindById(){
        Long id = 1L;
        Category category = new Category();
        category.setId(id);
        category.setName("Test");
        Mockito.when(categoryRepository.findById(id)).thenReturn(Optional.of(category));

        CategoryDto result = categoryService.findById(id);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("Test", result.getName());
        Mockito.verify(categoryRepository).findById(id);
    }

    @Test
    void testFindById_NotFound(){
        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(RuntimeException.class, () -> categoryService.findById(1L));
        Mockito.verify(categoryRepository).findById(1L);
    }
    @Test
    void testCreateCategory(){
        Category category = new Category();
        category.setName("Test");
        Mockito.when(categoryRepository.save(category)).thenReturn(category);

        CategoryDto result = categoryService.createCategory(category);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("Test",result.getName());
        Mockito.verify(categoryRepository).save(category);
    }

    @Test
    void testUpdateCategory(){
        Category oldCategory = new Category();
        oldCategory.setId(1L);
        oldCategory.setName("Test");

        Category updatedCategory = new Category();
        updatedCategory.setName("New test name");

        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.of(oldCategory));
        Mockito.when(categoryRepository.save(oldCategory)).thenReturn(oldCategory);

        CategoryDto result = categoryService.updateCategory(updatedCategory, 1L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("New test name",result.getName());
        Assertions.assertEquals("New test name", oldCategory.getName());
        Mockito.verify(categoryRepository).findById(1L);
        Mockito.verify(categoryRepository).save(oldCategory);
    }

    @Test
    void testDeleteById(){
        Long id = 1L;

        categoryService.deleteById(1L);

        Mockito.verify(categoryRepository).deleteById(1L);
    }
}
