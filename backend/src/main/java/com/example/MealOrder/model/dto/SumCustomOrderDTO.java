package com.example.MealOrder.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SumCustomOrderDTO {
    private String name;
    private int price;
    private Long quantity;
    private String firstName;
    private String lastName;
}
