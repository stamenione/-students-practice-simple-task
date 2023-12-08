package com.example.MealOrder.repository;

import com.example.MealOrder.model.MealEntity;
import com.example.MealOrder.model.MenuEntity;
import com.example.MealOrder.model.MenuMealEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MenuMealRepository extends JpaRepository<MenuMealEntity, Integer> {

    List<MenuMealEntity> findByMenuEntity(MenuEntity menuEntity);

    MenuMealEntity findByMenuEntityAndMealEntityAndMealDate(MenuEntity menuEntity, MealEntity mealEntity, LocalDate mealDate);
}
