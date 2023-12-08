package com.example.MealOrder.validators;

import com.example.MealOrder.constraints.NumberConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NumberValidator implements ConstraintValidator<NumberConstraint, String> {

    @Override
    public void initialize(NumberConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return !(s.equals("") || s == null);
    }
}
