package com.RestApiExample.demo.dto;

import com.RestApiExample.demo.models.Category;
import com.RestApiExample.demo.models.Food;
import lombok.Data;

import java.util.List;

@Data
public class CategoryDto {
    private Long id;
    private String name;
    private String description;
    private List<String> food;

    public CategoryDto(Category category){
        id = category.getId();
        name = category.getName();
        description = category.getDescription();
        food = (category.getFood() != null)
                ? category.getFood().stream()
                .map(Food::getName)
                .toList()
                : List.of();
    }
}
