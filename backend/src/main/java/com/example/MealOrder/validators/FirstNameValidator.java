package com.example.MealOrder.validators;

import com.example.MealOrder.constraints.FirstNameConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FirstNameValidator implements ConstraintValidator<FirstNameConstraint, String> {

    @Override
    public void initialize(FirstNameConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return !(s.equals("") || s == null);
    }
}
