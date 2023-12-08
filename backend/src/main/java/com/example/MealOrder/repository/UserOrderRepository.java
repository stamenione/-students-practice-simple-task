package com.example.MealOrder.repository;

import com.example.MealOrder.model.UserEntity;
import com.example.MealOrder.model.UserOrderEntity;
import com.example.MealOrder.model.dto.SumOrderDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;


public interface UserOrderRepository extends JpaRepository<UserOrderEntity, Integer> {

    List<UserOrderEntity> findByUserEntity(UserEntity userEntity);

    @Query("SELECT u FROM UserEntity u, UserOrderEntity uo, MenuMealEntity mm WHERE " +
            "u.id=uo.userEntity.id AND uo.menuMealEntity.id = mm.id and " +
            "mm.mealDate =:now")
    List<UserEntity> findByUserWitDate(LocalDate now);

    @Query("SELECT uoe FROM UserEntity  u, UserOrderEntity uoe, MenuMealEntity mm " +
            "WHERE uoe.userEntity.id = u.id AND uoe.menuMealEntity.id = mm.id " +
            "AND uoe.menuMealEntity.mealDate = :now")
    List<UserOrderEntity> findByUserMealsForToday(LocalDate now);


    @Query("SELECT new com.example.MealOrder.model.dto.SumOrderDTO(m.name, SUM(o.quantity), mm.mealDate)  FROM UserOrderEntity o, MenuMealEntity mm, MealEntity m " +
            "WHERE o.menuMealEntity.id = mm.id AND mm.mealEntity.id=m.id AND mm.mealDate = :date " +
            "GROUP BY o.menuMealEntity.id")
    List<SumOrderDTO> findOrdersForDate(LocalDate date);
}
