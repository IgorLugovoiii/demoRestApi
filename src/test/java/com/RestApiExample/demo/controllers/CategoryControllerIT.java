package com.RestApiExample.demo.controllers;

import com.RestApiExample.demo.models.Category;
import com.RestApiExample.demo.models.Food;
import com.RestApiExample.demo.repositories.CategoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @BeforeEach
    void setUp(){
        categoryRepository.deleteAll();
    }

    @Test
    void testGetAllCategories() throws Exception{
        Category category1 = new Category();
        category1.setName("Test name1");
        Category category2 = new Category();
        category2.setName("Test name2");

        categoryRepository.saveAll(List.of(category1,category2));

        mockMvc.perform(get("/api/category"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("Test name1"))
                .andExpect(jsonPath("$[1].name").value("Test name2"));
    }

    @Test
    void testFindById() throws Exception{
        Category category = new Category();
        category.setName("Test name");

        Category result = categoryRepository.save(category);

        mockMvc.perform(get("/api/category/{id}",result.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test name"));
    }

    @Test
    void testCreateCategory() throws Exception{
        Category category = new Category();
        category.setName("Test name");

        mockMvc.perform(post("/api/category")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test name"));
    }

    @Test
    void testUpdateCategory() throws Exception{
        Category category = new Category();
        category.setName("Test name");

        Category savedCategory = categoryRepository.save(category);
        savedCategory.setName("New test name");

        mockMvc.perform(put("/api/category/{id}",savedCategory.getId())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New test name"));
    }

    @Test
    void testDeleteCategory() throws Exception{
        Category category = new Category();
        category.setName("Test name");

        Category savedCategory = categoryRepository.save(category);

        mockMvc.perform(delete("/api/category/{id}",savedCategory.getId()))
                .andExpect(status().isNoContent());
    }
}
