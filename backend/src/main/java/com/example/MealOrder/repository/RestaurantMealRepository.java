package com.example.MealOrder.repository;

import com.example.MealOrder.model.CustomMealEntity;
import com.example.MealOrder.model.CustomRestaurantEntity;
import com.example.MealOrder.model.RestaurantMealEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestaurantMealRepository extends JpaRepository<RestaurantMealEntity, Integer> {

    RestaurantMealEntity findByCustomMealEntityAndCustomRestaurantEntity(CustomMealEntity customMealEntity, CustomRestaurantEntity customRestaurantEntity);

    @Query("SELECT f FROM RestaurantMealEntity f WHERE f.customMealEntity.id = :idMeal" +
            " AND f.customRestaurantEntity.id = :idRestaurant")
    RestaurantMealEntity findByRestaurantAndMeal(Integer idRestaurant, Integer idMeal);


    List<RestaurantMealEntity> findAllByCustomRestaurantEntity(CustomRestaurantEntity customRestaurantEntity);

}
