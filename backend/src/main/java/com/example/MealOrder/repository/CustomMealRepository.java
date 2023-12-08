package com.example.MealOrder.repository;

import com.example.MealOrder.model.CustomMealEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomMealRepository extends JpaRepository<CustomMealEntity, Integer> {

    CustomMealEntity findByNameAndPrice(String name, int price);

}
