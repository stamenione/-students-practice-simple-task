package com.example.MealOrder.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDTO {

    private String email;
    private LocalDate menuStartingDate;
    private String name;
    private int price;
    private LocalDate mealDate;
    private String mealType;
    private int quantity;
}
