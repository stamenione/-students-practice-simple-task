package com.example.MealOrder.model.dto;

import com.example.MealOrder.constraints.CustomMealNameConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomMealDTO {

    @CustomMealNameConstraint
    private String name;
    private int price;
    private byte willOrder;
    private int quantity;
}
