package com.RestApiExample.demo.services;

import com.RestApiExample.demo.models.Food;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.RestApiExample.demo.repositories.FoodRepository;

import java.util.List;


@Service
public class FoodService {
    private final FoodRepository foodRepository;
    @Autowired
    public FoodService(FoodRepository foodRepository){
        this.foodRepository = foodRepository;
    }
    public List<Food> getAllFood(){
        return foodRepository.findAll();
    }
    public Food findById(Long id){
        return foodRepository.findById(id).orElseThrow(() -> new RuntimeException("Food don`t exist"));
    }
    public Food createFood(Food food){
        return foodRepository.save(food);
    }
    public Food updateFood(Long id, Food updatedFood){
        Food existingFood = findById(id);
        existingFood.setName(updatedFood.getName());
        existingFood.setDescription(updatedFood.getDescription());
        existingFood.setPrice(updatedFood.getPrice());
        existingFood.setCategory(updatedFood.getCategory());
        return foodRepository.save(existingFood);
    }
    public void deleteById(Long id){
        foodRepository.deleteById(id);
    }
}
