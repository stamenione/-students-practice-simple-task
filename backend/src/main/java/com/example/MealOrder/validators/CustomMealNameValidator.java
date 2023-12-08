package com.example.MealOrder.validators;

import com.example.MealOrder.constraints.CustomMealNameConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CustomMealNameValidator implements ConstraintValidator<CustomMealNameConstraint, String> {

    @Override
    public void initialize(CustomMealNameConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return !(s.equals("") || s == null);
    }
}
