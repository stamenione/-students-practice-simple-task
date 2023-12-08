package com.example.MealOrder.validators;

import com.example.MealOrder.constraints.RestaurantNameConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RestaurantNameValidator implements ConstraintValidator<RestaurantNameConstraint, String> {

    @Override
    public void initialize(RestaurantNameConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return !(s.equals("") || s == null);
    }

}
