package com.example.MealOrder.repository;

import com.example.MealOrder.model.CustomOrderEntity;
import com.example.MealOrder.model.RestaurantMealEntity;
import com.example.MealOrder.model.dto.SumCustomOrderDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomOrderRepository extends JpaRepository<CustomOrderEntity, Integer> {


    @Query("SELECT new com.example.MealOrder.model.dto.SumCustomOrderDTO(m.name, m.price, SUM(o.quantity), u.firstName, u.lastName)  FROM CustomRestaurantEntity r, RestaurantMealEntity rm, CustomOrderEntity o, CustomMealEntity m, UserEntity u " +
            "WHERE o.restaurantMealEntity.id=rm.id AND rm.customRestaurantEntity.id=r.id AND rm.customMealEntity.id=m.id AND r.name = :name AND o.userEntity.id=u.id " +
            "GROUP BY u.id, m.id")
    List<SumCustomOrderDTO> findCustomOrdersForRestaurant(String name);


    List<CustomOrderEntity> findAllByRestaurantMealEntity(RestaurantMealEntity restaurantMealEntity);
}
