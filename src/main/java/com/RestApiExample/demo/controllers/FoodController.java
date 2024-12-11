package com.RestApiExample.demo.controllers;

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
    public ResponseEntity<List<Food>> getAllFood(){
        List<Food> food = foodService.getAllFood();
        return new ResponseEntity<>(food, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Food> findById(@PathVariable Long id){//PathVariable передає в "/{id}"
        Food food = foodService.findById(id);
        return new ResponseEntity<>(food, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Food> createFood(@RequestBody Food food){ //RequestBody приймає json об`єкт і перетворює в java object
        Food createdFood = foodService.createFood(food);
        return new ResponseEntity<>(createdFood, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Food> updateFood(@PathVariable Long id, @RequestBody Food updatedFood){
        Food food = foodService.updateFood(id, updatedFood);
        return new ResponseEntity<>(food, HttpStatus.OK);//ResponseEntity тіло фуд перетворюється в json об'єкт і повертає статус ок
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFood(@PathVariable Long id){
        foodService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
