package com.example.MealOrder.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MenuDTO {

    private LocalDate startDate;
    private String description;
    private String image;
    private List<MealDTO> meals;
}
