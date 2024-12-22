package com.RestApiExample.demo.services;

import com.RestApiExample.demo.dto.FoodDto;
import com.RestApiExample.demo.models.Food;
import com.RestApiExample.demo.repositories.FoodRepository;
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
public class FoodServiceTest {
    @Mock
    private FoodRepository foodRepository;
    @InjectMocks
    private FoodService foodService;
    @Test
    void testGetAllFood(){
        Food food = new Food();
        food.setId(1L);
        food.setName("Test name");
        List<Food> foodList = List.of(food);

        Mockito.when(foodRepository.findAll()).thenReturn(foodList);

        List<FoodDto> foodDto = foodService.getAllFood();

        Assertions.assertNotNull(foodDto);
        Assertions.assertEquals("Test name", foodDto.getFirst().getName());
        Assertions.assertEquals(1, foodDto.size());
        Mockito.verify(foodRepository,Mockito.times(1)).findAll();
    }

    @Test
    void testFindById(){
        Food food = new Food();
        food.setId(1L);
        food.setName("Test name");

        Mockito.when(foodRepository.findById(1L)).thenReturn(Optional.of(food));

        FoodDto result = foodService.findById(1L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("Test name", result.getName());
        Assertions.assertEquals(1L,result.getId());
        Mockito.verify(foodRepository).findById(1L);
    }

    @Test
    void testCreateFood(){
        Food food = new Food();
        food.setId(1L);
        food.setName("Test name");

        Mockito.when(foodRepository.save(food)).thenReturn(food);

        FoodDto result = foodService.createFood(food);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("Test name", result.getName());
        Mockito.verify(foodRepository).save(food);
    }

    @Test
    void testUpdateFood(){
        Food oldFood = new Food();
        oldFood.setId(1L);
        oldFood.setName("Test name");
        Food updatedFood = new Food();
        updatedFood.setName("New test name");

        Mockito.when(foodRepository.findById(1L)).thenReturn(Optional.of(oldFood));
        Mockito.when(foodRepository.save(oldFood)).thenReturn(oldFood);

        foodService.updateFood(1L,updatedFood);

        Assertions.assertNotNull(oldFood);
        Assertions.assertEquals("New test name", oldFood.getName());
        Mockito.verify(foodRepository).findById(1L);
        Mockito.verify(foodRepository).save(oldFood);
    }

    @Test
    void testDeleteFood(){
        Long id = 1L;

        foodService.deleteById(1L);

        Mockito.verify(foodRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void testFindByPriceLessThan(){
        Food food1 = new Food();
        Food food2 = new Food();

        food1.setName("Food1");
        food1.setPrice(15.00);
        food2.setName("Food2");
        food2.setPrice(25.00);

        Mockito.when(foodRepository.findAll()).thenReturn(List.of(food1,food2));

        List<FoodDto> foodDtoList = foodService.findByPriceLessThan(20.00);

        Assertions.assertEquals("Food1",foodDtoList.getFirst().getName());
        Assertions.assertEquals(1, foodDtoList.size());
        Mockito.verify(foodRepository, Mockito.times(1)).findAll();
    }

    @Test
    void testFindByPriceMoreThan(){
        Food food1 = new Food();
        Food food2 = new Food();

        food1.setName("Food1");
        food1.setPrice(15.00);
        food2.setName("Food2");
        food2.setPrice(25.00);

        Mockito.when(foodRepository.findAll()).thenReturn(List.of(food1,food2));

        List<FoodDto> foodDtoList = foodService.findByPriceMoreThan(20.00);

        Assertions.assertEquals(1, foodDtoList.size());
        Assertions.assertEquals("Food2",foodDtoList.getFirst().getName());
        Mockito.verify(foodRepository, Mockito.times(1)).findAll();
    }

    @Test
    void testSortFoodByIncreasingPrice(){
        Food food1 = new Food();
        Food food2 = new Food();

        food1.setName("Food1");
        food1.setPrice(25.00);
        food2.setName("Food2");
        food2.setPrice(15.00);

        Mockito.when(foodRepository.findAll()).thenReturn(List.of(food1,food2));

        List<FoodDto> list = foodService.sortFoodByIncreasingPrice();

        Assertions.assertEquals("Food2", list.getFirst().getName());
        Assertions.assertEquals("Food1", list.get(1).getName());
        Assertions.assertEquals(2, list.size());
        Mockito.verify(foodRepository).findAll();
    }

    @Test
    void testSortFoodByDecreasingPrice(){
        Food food1 = new Food();
        Food food2 = new Food();

        food1.setName("Food1");
        food1.setPrice(25.00);
        food2.setName("Food2");
        food2.setPrice(15.00);

        Mockito.when(foodRepository.findAll()).thenReturn(List.of(food1,food2));

        List<FoodDto> list = foodService.sortFoodByDecreasingPrice();

        Assertions.assertEquals("Food1", list.getFirst().getName());
        Assertions.assertEquals("Food2", list.get(1).getName());
        Assertions.assertEquals(2, list.size());
        Mockito.verify(foodRepository).findAll();
    }
}
