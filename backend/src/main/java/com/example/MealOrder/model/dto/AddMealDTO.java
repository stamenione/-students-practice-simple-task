package com.example.MealOrder.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AddMealDTO {

    private String name;

    private int price;

    private byte dayBefore;

    private String mealType;
}
