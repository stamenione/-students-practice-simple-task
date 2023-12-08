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
public class SumOrderDTO {
    private String name;
    private Long quantity;
    private LocalDate mealDate;

}
