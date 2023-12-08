package com.example.MealOrder.validators;

import com.example.MealOrder.constraints.StreetNameConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StreetNameValidator implements ConstraintValidator<StreetNameConstraint, String> {

    @Override
    public void initialize(StreetNameConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return !(s.equals("") || s == null);
    }
}
