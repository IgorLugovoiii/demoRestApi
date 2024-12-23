package com.RestApiExample.demo.controllers;

import com.RestApiExample.demo.models.Food;
import com.RestApiExample.demo.repositories.FoodRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FoodControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private ObjectMapper objectMapper;// перетворює java класи в json і навпаки
    @BeforeEach//метод буде виконуватись перед кожним тестом.
    void setUp(){
        foodRepository.deleteAll();
    }

    @Test
    void testGetAllFood_ReturnsAllFoodList() throws Exception{
        Food food1 = new Food();
        Food food2 = new Food();
        food1.setName("Test name1");
        food1.setPrice(11.00);
        food2.setName("Test name2");
        food2.setPrice(12.00);

        foodRepository.saveAll(List.of(food1,food2));

        mockMvc.perform(get("/api/food"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("Test name1"))
                .andExpect(jsonPath("$[1].name").value("Test name2"));
    }

    @Test
    void testFindById() throws Exception{
        Food food = new Food();
        food.setName("Test name");
        food.setPrice(12.00);

        Food savedFood = foodRepository.save(food);

        mockMvc.perform(get("/api/food/{id}",savedFood.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test name"))
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testCreateFood() throws Exception{
        Food food = new Food();
        food.setName("Test name");
        food.setPrice(20.00);

        mockMvc.perform(post("/api/food")
                .contentType("application/json")//вказуємо, що тіло запиту має формат JSON, тому що використовую анотацію @RequestBody, яка буде парсити тіло запиту з JSON у Java-об'єкти.
                .content(objectMapper.writeValueAsString(food)))// а тут перетворюємо java об'єкт у json
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test name"))
                .andExpect(jsonPath("$.price").value(20.0));
    }

    @Test
    void testUpdateFood() throws Exception{
        Food food = new Food();
        food.setName("Test name");
        food.setPrice(20.00);

        Food savedFood = foodRepository.save(food);
        savedFood.setName("Updated name");
        savedFood.setPrice(15.00);

        mockMvc.perform(put("/api/food/{id}",savedFood.getId())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(savedFood)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated name"))
                .andExpect(jsonPath("$.price").value(15.00));
    }

    @Test
    void testDeleteFood() throws Exception{
        Food food = new Food();
        food.setName("Test name");
        food.setPrice(15.00);

        Food savedFood = foodRepository.save(food);

        mockMvc.perform(delete("/api/food/{id}", savedFood.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void testFindByPriceLessThan() throws Exception{
        Food food1 = new Food();
        food1.setName("Test name1");
        food1.setPrice(15.00);

        Food food2 = new Food();
        food2.setName("Test name2");
        food2.setPrice(20.00);

        foodRepository.saveAll(List.of(food1,food2));

        mockMvc.perform(get("/api/food/less-than/{price}",16.00))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test name1"))
                .andExpect(jsonPath("$[0].price").value(15.00))
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    void testFindByPriceMoreThan() throws Exception{
        Food food1 = new Food();
        food1.setName("Test name1");
        food1.setPrice(15.00);

        Food food2 = new Food();
        food2.setName("Test name2");
        food2.setPrice(20.00);

        foodRepository.saveAll(List.of(food1,food2));

        mockMvc.perform(get("/api/food/more-than/{price}",16.00))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test name2"))
                .andExpect(jsonPath("$[0].price").value(20.00))
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    void testSortFoodByIncreasingPrice() throws Exception{
        Food food1 = new Food();
        food1.setName("Test name1");
        food1.setPrice(15.00);

        Food food2 = new Food();
        food2.setName("Test name2");
        food2.setPrice(20.00);

        foodRepository.saveAll(List.of(food1,food2));

        mockMvc.perform(get("/api/food/increasing-price"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test name1"))
                .andExpect(jsonPath("$[1].name").value("Test name2"));
    }
    @Test
    void testSortFoodByDecreasingPrice() throws Exception{
        Food food1 = new Food();
        food1.setName("Test name1");
        food1.setPrice(15.00);

        Food food2 = new Food();
        food2.setName("Test name2");
        food2.setPrice(20.00);

        foodRepository.saveAll(List.of(food1,food2));

        mockMvc.perform(get("/api/food/decreasing-price"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test name2"))
                .andExpect(jsonPath("$[1].name").value("Test name1"));
    }
}
