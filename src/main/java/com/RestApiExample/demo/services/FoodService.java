package com.RestApiExample.demo.services;

import com.RestApiExample.demo.dto.FoodDto;
import com.RestApiExample.demo.models.Category;
import com.RestApiExample.demo.models.Food;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.RestApiExample.demo.repositories.FoodRepository;

import java.util.Comparator;
import java.util.List;

@Service
public class FoodService {
    private final FoodRepository foodRepository;
    @Autowired
    public FoodService(FoodRepository foodRepository){
        this.foodRepository = foodRepository;
    }
    private FoodDto convertToFoodDto(Food food){
        return new FoodDto(food);
    }
    public List<FoodDto> getAllFood(){

        return foodRepository.findAll()
                .stream()
                .map(this::convertToFoodDto) //food->convertToFoodDto(food) кожен елемент food перетворюємо в FoodDto
                .toList();
    }
    public FoodDto findById(Long id){
        Food food = foodRepository.findById(id).orElseThrow(() -> new RuntimeException("Food don`t exist"));
        return convertToFoodDto(food);
    }
    public FoodDto createFood(Food food){
        return convertToFoodDto(foodRepository.save(food));
    }
    public FoodDto updateFood(Long id, Food updatedFood){
        Food existingFood = foodRepository.findById(id).orElseThrow(() -> new RuntimeException("Food don`t exist"));
        existingFood.setName(updatedFood.getName());
        existingFood.setDescription(updatedFood.getDescription());
        existingFood.setPrice(updatedFood.getPrice());
        existingFood.setCategory(updatedFood.getCategory());

        return convertToFoodDto(foodRepository.save(existingFood));
    }
    public void deleteById(Long id){
        foodRepository.deleteById(id);
    }
    public List<FoodDto> findFoodByCategory(Category category){
        return foodRepository.findAll().stream()
                .filter(food -> food.getCategory().equals(category))
                .map(this::convertToFoodDto)
                .toList();
    }
    public List<FoodDto> findByPriceLessThan(Double price){
        return foodRepository.findAll().stream()
                .filter(food -> food.getPrice() < price)
                .map(this::convertToFoodDto)
                .toList();
    }
    public List<FoodDto> findByPriceMoreThan(Double price){
        return foodRepository.findAll().stream()
                .filter(food -> food.getPrice() > price)
                .map(this::convertToFoodDto)
                .toList();
    }
    public List<FoodDto> sortFoodByIncreasingPrice(){
        return foodRepository.findAll().stream()
                .sorted(Comparator.comparingDouble(Food::getPrice))
                .map(this::convertToFoodDto)
                .toList();
    }
    public List<FoodDto> sortFoodByDecreasingPrice(){
        return foodRepository.findAll().stream()
                .sorted(Comparator.comparingDouble(Food::getPrice).reversed())
                .map(this::convertToFoodDto)
                .toList();
    }
}
