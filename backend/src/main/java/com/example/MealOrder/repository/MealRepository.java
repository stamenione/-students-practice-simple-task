package com.example.MealOrder.repository;

import com.example.MealOrder.model.MealEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealRepository extends JpaRepository<MealEntity, Integer> {

    MealEntity findByName(String name);

    MealEntity findByNameAndPrice(String name, int price);


}
