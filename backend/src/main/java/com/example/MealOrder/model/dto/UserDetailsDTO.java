package com.example.MealOrder.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class UserDetailsDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String number;
    private String streetName;
    private String streetNumber;
    private byte theChosenOne;
}
