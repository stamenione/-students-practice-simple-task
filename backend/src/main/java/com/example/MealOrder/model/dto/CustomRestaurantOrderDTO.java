package com.example.MealOrder.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomRestaurantOrderDTO {

    private String mealName;
    private Integer mealPrice;
    private String restaurantName;
    private String email;
    private Integer quantity;

}
