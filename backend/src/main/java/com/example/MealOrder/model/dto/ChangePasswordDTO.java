package com.example.MealOrder.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChangePasswordDTO {
    private String email;
    private String oldPassword;
    private String newPassword;
}
