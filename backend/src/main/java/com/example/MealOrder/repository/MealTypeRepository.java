package com.example.MealOrder.repository;

import com.example.MealOrder.model.MealTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealTypeRepository extends JpaRepository<MealTypeEntity, Integer> {

    MealTypeEntity findByType(String type);

}
