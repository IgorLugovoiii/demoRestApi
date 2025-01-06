package com.RestApiExample.demo.controllers;

import com.RestApiExample.demo.dto.FoodDto;
import com.RestApiExample.demo.models.Category;
import com.RestApiExample.demo.services.FoodService;
import com.RestApiExample.demo.models.Food;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {
    private final FoodService foodService;
    @Autowired
    public FoodController(FoodService foodService){
        this.foodService=foodService;
    }
    @GetMapping
    public ResponseEntity<List<FoodDto>> getAllFood(){
        List<FoodDto> food = foodService.getAllFood();
        return new ResponseEntity<>(food, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<FoodDto> findById(@PathVariable Long id){//PathVariable передає в "/{id}"
        FoodDto food = foodService.findById(id);
        return new ResponseEntity<>(food, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<FoodDto> createFood(@RequestBody Food food){ //RequestBody приймає json об`єкт і перетворює в java object
        FoodDto createdFood = foodService.createFood(food);
        return new ResponseEntity<>(createdFood, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<FoodDto> updateFood(@PathVariable Long id, @RequestBody Food updatedFood){
        FoodDto food = foodService.updateFood(id, updatedFood);
        return new ResponseEntity<>(food, HttpStatus.OK);//ResponseEntity тіло фуд перетворюється в json об'єкт і повертає статус ок
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFood(@PathVariable Long id){
        foodService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/less-than/{price}")
    public ResponseEntity<List<FoodDto>> findByPriceLessThan(@PathVariable Double price){
        List<FoodDto> foodList = foodService.findByPriceLessThan(price);
        return new ResponseEntity<>(foodList, HttpStatus.OK);
    }
    @GetMapping("/more-than/{price}")
    public ResponseEntity<List<FoodDto>> findByPriceGreaterThan(@PathVariable Double price){
        List<FoodDto> foodList = foodService.findByPriceGreaterThan(price);
        return new ResponseEntity<>(foodList, HttpStatus.OK);
    }
    @GetMapping("/increasing-price")
    public ResponseEntity<List<FoodDto>> sortFoodByIncreasingPrice(){
        List<FoodDto> foodList = foodService.sortFoodByIncreasingPrice();
        return new ResponseEntity<>(foodList, HttpStatus.OK);
    }
    @GetMapping("/decreasing-price")
    public ResponseEntity<List<FoodDto>> sortFoodByDecreasingPrice(){
        List<FoodDto> foodList = foodService.sortFoodByDecreasingPrice();
        return new ResponseEntity<>(foodList, HttpStatus.OK);
    }
//    @GetMapping("findByCategory/{categoryName}")
//    public ResponseEntity<List<FoodDto>> findFoodByCategory(@PathVariable String categoryName){
//        List<FoodDto> foodDtoList = foodService.findFoodByCategory(categoryName);
//        return new ResponseEntity<>(foodDtoList, HttpStatus.OK);
//    }
}
