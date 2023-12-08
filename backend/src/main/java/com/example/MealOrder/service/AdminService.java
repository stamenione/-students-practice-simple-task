package com.example.MealOrder.service;

import com.example.MealOrder.model.dto.AddMealDTO;
import com.example.MealOrder.model.dto.AddMenuDTO;
import com.example.MealOrder.model.dto.AddMenuMealDTO;

import java.time.LocalDate;
import java.util.List;

public interface AdminService {

    boolean addMeal(AddMealDTO addMealDTO);

    boolean addMenu(AddMenuDTO addMenuDTO);

    boolean addMenuMeal(AddMenuMealDTO addMenuMealDTO);

    List<LocalDate> allUsedDatesForMenus();

    List<String> allMealTypes();

    List<String> nextMenus();

    List<String> allMeals();

}
