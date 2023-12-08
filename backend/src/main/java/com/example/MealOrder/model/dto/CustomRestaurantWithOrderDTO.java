package com.example.MealOrder.model.dto;

import com.example.MealOrder.constraints.LocalTimeNotNullConstraint;
import com.example.MealOrder.constraints.RestaurantNameConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import java.time.LocalTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomRestaurantWithOrderDTO {

    @RestaurantNameConstraint
    private String restaurantName;

    @LocalTimeNotNullConstraint
    private LocalTime orderTime;

    @Valid
    private List<CustomMealDTO> meals;

}
