package com.example.MealOrder.model.mapper;

import com.example.MealOrder.model.AddressEntity;
import com.example.MealOrder.model.CustomMealEntity;
import com.example.MealOrder.model.MenuEntity;
import com.example.MealOrder.model.UserEntity;
import com.example.MealOrder.model.dto.*;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@NoArgsConstructor
public class Mapper {

    public UserOrderDTO newUserOrderDTO(UserEntity userEntity, AddressEntity addressEntity, int needToPay, byte payed) {

        return new UserOrderDTO(userEntity.getFirstName(), userEntity.getLastName(), userEntity.getEmail(), needToPay, payed);
    }

    public MenuDTO newMenuDTO(MenuEntity menuEntity, List<MealDTO> meals) {
        return new MenuDTO(menuEntity.getStartDate(), menuEntity.getDescription(), menuEntity.getImageEntity().getLink(), meals);
    }

    public UserDTO newUserDTO(UserEntity userEntity, AddressEntity addressEntity) {

        return new UserDTO(userEntity.getFirstName(), userEntity.getLastName(), userEntity.getEmail(), userEntity.getPassword(), userEntity.getNumber(), addressEntity.getStreetName(), addressEntity.getStreetNumber());
    }

    public UserDetailsDTO newUserDetailsDTO(UserEntity userEntity, AddressEntity addressEntity) {

        return new UserDetailsDTO(userEntity.getFirstName(), userEntity.getLastName(), userEntity.getEmail(), userEntity.getNumber(), addressEntity.getStreetName(), addressEntity.getStreetNumber(), userEntity.getTheChoosenOne());
    }

    public CustomMealOfRestaurantDTO newCustomMealOfRestaurantDTO(CustomMealEntity customMealEntity) {
        return new CustomMealOfRestaurantDTO(customMealEntity.getName(), customMealEntity.getPrice());
    }

}
