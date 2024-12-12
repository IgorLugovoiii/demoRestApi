package com.RestApiExample.demo.dto;

import com.RestApiExample.demo.models.Food;
import lombok.Data;

@Data
public class FoodDto {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String categoryName;
    public FoodDto(Food food){
        id = food.getId();
        name = food.getName();
        description = food.getDescription();
        price = food.getPrice();
        categoryName = (food.getCategory() != null) ? food.getCategory().getName() : "Без категорії";
    }

}
