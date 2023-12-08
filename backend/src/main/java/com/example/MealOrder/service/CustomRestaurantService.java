package com.example.MealOrder.service;

import com.example.MealOrder.model.dto.CustomMealOfRestaurantDTO;
import com.example.MealOrder.model.dto.CustomRestaurantOrderDTO;
import com.example.MealOrder.model.dto.CustomRestaurantWithOrderDTO;

import java.util.List;

public interface CustomRestaurantService {

    CustomRestaurantOrderDTO orderFromCustomRestaurant(CustomRestaurantOrderDTO customRestaurantOrderDTO);


    CustomRestaurantWithOrderDTO addCustomRestaurant(String token, CustomRestaurantWithOrderDTO customRestaurantWithOrderDTO);

    List<String> getAllRestaurants();


    List<CustomMealOfRestaurantDTO> getRestaurantMenu(String name);

}
