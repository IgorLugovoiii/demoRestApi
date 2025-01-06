package com.RestApiExample.demo.services;

import com.RestApiExample.demo.dto.FoodDto;
import com.RestApiExample.demo.models.Food;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.RestApiExample.demo.repositories.FoodRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
public class FoodService {
    private final FoodRepository foodRepository;
    private final Logger logger = LoggerFactory.getLogger(FoodService.class);
    @Autowired
    public FoodService(FoodRepository foodRepository){
        this.foodRepository = foodRepository;
    }
    private FoodDto convertToFoodDto(Food food){
        return new FoodDto(food);
    }
    @Transactional(readOnly = true)
    public List<FoodDto> getAllFood(){
        try{
            logger.debug("Attempting to find all food");
        List<FoodDto> foodDtos = foodRepository.findAll()
                .stream()
                .map(this::convertToFoodDto) //food->convertToFoodDto(food) кожен елемент food перетворюємо в FoodDto
                .toList();
        logger.debug("Found {} categories", foodDtos.size());
        return foodDtos;
        } catch (Exception e){
            logger.error("Failed to find all food due to {}",e.getMessage(),e);
            throw new RuntimeException("Unable to find any food", e);
        }

    }
    @Transactional(readOnly = true)
    public FoodDto findById(Long id){
        logger.debug("Looking for food with id: {}", id);
        try {
            Food food = foodRepository.findById(id)
                    .orElseThrow(() ->{
                        logger.warn("Food with id: {} not found", id);
                        return new EntityNotFoundException("Food don`t exist");
                    });
            return convertToFoodDto(food);
        } catch (Exception e){
            logger.error("Failed to find food with id {} due to {}", id, e.getMessage(),e);
            throw new RuntimeException("Unable to find food by id", e);
        }
    }
    @Transactional
    public FoodDto createFood(Food food){
        try {
            logger.debug("Attempting to create food: {}", food);
            return convertToFoodDto(foodRepository.save(food));
        } catch (Exception e){
            logger.error("Failed to create food due to {}", e.getMessage(), e);
            throw new RuntimeException("Unable to create food, please check the input data", e);
        }
    }
    @Transactional
    public FoodDto updateFood(Long id, Food updatedFood){
        try {
            logger.debug("Attempting to update food with id: {}", id);
            Food existingFood = foodRepository.findById(id)
                    .orElseThrow(() -> {
                        logger.warn("Food with id {} not found for update",id);
                        return new EntityNotFoundException("Food don`t exist");
                    });
            existingFood.setName(updatedFood.getName());
            existingFood.setDescription(updatedFood.getDescription());
            existingFood.setPrice(updatedFood.getPrice());
            existingFood.setCategory(updatedFood.getCategory());

            return convertToFoodDto(foodRepository.save(existingFood));
        } catch (Exception e){
            logger.error("Failed to update food with id {} due to {}", id, e.getMessage(), e);
            throw new RuntimeException("Unable to update food, please check the input data",e);
        }
    }
    @Transactional
    public void deleteById(Long id){
        try {
            logger.debug("Attempting to delete food with id: {}", id);
            foodRepository.deleteById(id);
            logger.debug("Food with id {} successfully deleted", id);
        } catch (EntityNotFoundException entityNotFoundException){
            logger.warn("Food with id {} not found for deletion  due to {}", id, entityNotFoundException.getMessage(),entityNotFoundException);
            throw entityNotFoundException;
        } catch (Exception e){
            logger.error("Failed to delete food with id {} due to {}", id, e.getMessage(),e);
            throw new RuntimeException("Failed to delete food, please check the input data", e);
        }
    }
    public List<FoodDto> findFoodByCategory(String categoryName){
        try {
            logger.debug("Attempting to find food by category {}", categoryName);
            List<FoodDto> foodDtos = foodRepository.findAll().stream()
                    .filter(food -> food.getCategory().getName().equals(categoryName))
                    .map(this::convertToFoodDto)
                    .toList();
            logger.debug("Food was found");
            return foodDtos;
        } catch (Exception e){
            logger.error("Food with category name {} not found  due to {}", categoryName, e.getMessage(),e);
            throw new RuntimeException("Failed to find food by category name", e);
        }
    }
    @Transactional(readOnly = true)
    public List<FoodDto> findByPriceLessThan(Double price){
        try {
            logger.debug("Attempting to find food by price less than price: {}", price);
            List<FoodDto> foodDtos = foodRepository.findByPriceLessThan(price).stream()
                    .map(this::convertToFoodDto)
                    .toList();
            logger.debug("Food was found by price less than price: {}", price);
            return foodDtos;
        } catch (Exception e){
            logger.error("Food less then {} was not found",price);
            throw new RuntimeException("Failed to find food",e);
        }
    }
    @Transactional(readOnly = true)
    public List<FoodDto> findByPriceGreaterThan(Double price){
        try {
            logger.debug("Attempting to find food by price more than price: {}", price);
            List<FoodDto> foodDtos = foodRepository.findByPriceGreaterThan(price).stream()
                .map(this::convertToFoodDto)
                .toList();
            logger.debug("Food was found by price more than price: {}", price);
        return foodDtos;
        } catch (Exception e){
            logger.error("Food more then {} was not found",price);
            throw new RuntimeException("Failed to find food",e);
        }
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
