package com.RestApiExample.demo.services;

import com.RestApiExample.demo.dto.FoodDto;
import com.RestApiExample.demo.models.Food;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.RestApiExample.demo.repositories.FoodRepository;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(readOnly = true)
    public List<FoodDto> getAllFood(){
        return foodRepository.findAll()
                .stream()
                .map(this::convertToFoodDto) //food->convertToFoodDto(food) кожен елемент food перетворюємо в FoodDto
                .toList();
    }
    @Transactional(readOnly = true)
    public FoodDto findById(Long id){
        Food food = foodRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Food don`t exist"));
            return convertToFoodDto(food);
    }
    @Transactional
    public FoodDto createFood(Food food){
        return convertToFoodDto(foodRepository.save(food));
    }
    @Transactional
    public FoodDto updateFood(Long id, Food updatedFood){
        Food existingFood = foodRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Food don`t exist"));
        existingFood.setName(updatedFood.getName());
        existingFood.setDescription(updatedFood.getDescription());
        existingFood.setPrice(updatedFood.getPrice());
        existingFood.setCategory(updatedFood.getCategory());

        return convertToFoodDto(foodRepository.save(existingFood));
    }
    @Transactional
    public void deleteById(Long id){
        foodRepository.deleteById(id);
    }
    public List<FoodDto> findFoodByCategory(String categoryName){
        return foodRepository.findAll().stream()
                .filter(food -> food.getCategory().getName().equals(categoryName))
                .map(this::convertToFoodDto)
                .toList();
    }
    @Transactional(readOnly = true)
    public List<FoodDto> findByPriceLessThan(Double price){
        return foodRepository.findByPriceLessThan(price).stream()
                    .map(this::convertToFoodDto)
                    .toList();
    }
    @Transactional(readOnly = true)
    public List<FoodDto> findByPriceGreaterThan(Double price){
        return foodRepository.findByPriceGreaterThan(price).stream()
            .map(this::convertToFoodDto)
            .toList();

    }
    @Transactional(readOnly = true)
    public List<FoodDto> sortFoodByIncreasingPrice(){
        return foodRepository.sortFoodByIncreasingPrice().stream()
                .map(this::convertToFoodDto)
                .toList();
    }
    @Transactional(readOnly = true)
    public List<FoodDto> sortFoodByDecreasingPrice(){
        return foodRepository.sortFoodByDecreasingPrice().stream()
                .map(this::convertToFoodDto)
                .toList();
    }
}
