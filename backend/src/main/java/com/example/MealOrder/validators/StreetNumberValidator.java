package com.example.MealOrder.validators;

import com.example.MealOrder.constraints.StreetNumberConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StreetNumberValidator implements ConstraintValidator<StreetNumberConstraint, String> {

    @Override
    public void initialize(StreetNumberConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return !(s.equals("") || s == null);
    }
}
