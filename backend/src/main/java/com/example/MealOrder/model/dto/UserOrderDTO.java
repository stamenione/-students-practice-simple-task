package com.example.MealOrder.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class UserOrderDTO {

    private String firstName;
    private String lastName;
    private String email;
    private int needToPay;
    private int payed;
}
