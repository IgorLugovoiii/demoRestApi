package com.RestApiExample.demo.services;


import com.RestApiExample.demo.models.Category;
import com.RestApiExample.demo.repositories.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository; // Мок для репозиторію

    @InjectMocks
    private CategoryService categoryService;
    @Test
    public void testFindAllCategories(){
        List<Category> mockCategories = List.of(new Category());
        when(categoryRepository.findAll()).thenReturn(mockCategories);

        List<Category> categories = categoryService.findAllCategories();

        assertEquals(1, categories.size());
        verify(categoryRepository, times(1)).findAll();
    }
    @Test
    public void testFindById(){
        Long id = 1L;
        Category mockCategory = new Category();
        mockCategory.setId(id);
        when(categoryRepository.findById(id)).thenReturn(Optional.of(mockCategory));

        Category category = categoryService.findById(id);

        assertNotNull(category);
        assertEquals(id, category.getId());
    }


}
