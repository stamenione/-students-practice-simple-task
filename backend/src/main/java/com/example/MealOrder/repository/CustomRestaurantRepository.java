package com.example.MealOrder.repository;

import com.example.MealOrder.model.CustomRestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomRestaurantRepository extends JpaRepository<CustomRestaurantEntity, Integer> {

    CustomRestaurantEntity findByName(String name);

}
