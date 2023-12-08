package com.example.MealOrder.validators;

import com.example.MealOrder.constraints.NoNegativeNumberConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NoNegativeNumberValidator implements ConstraintValidator<NoNegativeNumberConstraint, Integer> {

    @Override
    public void initialize(NoNegativeNumberConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer s, ConstraintValidatorContext constraintValidatorContext) {
        return s > 0;
    }
}
