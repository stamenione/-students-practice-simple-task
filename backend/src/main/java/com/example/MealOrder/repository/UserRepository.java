package com.example.MealOrder.repository;

import com.example.MealOrder.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByEmail(String email);

    @Query("SELECT u FROM UserEntity u WHERE u.theChoosenOne =:theChoosen")
    UserEntity findByChoosenOne(byte theChoosen);

    List<UserEntity> findAllByRoleEntity_Name(String label);

}
