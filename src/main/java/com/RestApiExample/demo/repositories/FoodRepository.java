package com.RestApiExample.demo.repositories;

import com.RestApiExample.demo.models.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findByPriceLessThan(Double price);
    List<Food> findByPriceGreaterThan(Double price);
    @Query("SELECT f FROM Food f ORDER BY f.price ASC")
    List<Food> sortFoodByIncreasingPrice();
    @Query("SELECT f FROM Food f ORDER BY f.price DESC")
    List<Food> sortFoodByDecreasingPrice();
}
