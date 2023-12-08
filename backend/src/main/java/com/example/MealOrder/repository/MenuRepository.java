package com.example.MealOrder.repository;

import com.example.MealOrder.model.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface MenuRepository extends JpaRepository<MenuEntity, Integer> {

    MenuEntity findByStartDate(LocalDate start_date);

}
