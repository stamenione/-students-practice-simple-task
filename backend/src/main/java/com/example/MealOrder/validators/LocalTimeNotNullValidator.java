package com.example.MealOrder.validators;

import com.example.MealOrder.constraints.LocalTimeNotNullConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalTime;

public class LocalTimeNotNullValidator implements ConstraintValidator<LocalTimeNotNullConstraint, LocalTime> {

    @Override
    public void initialize(LocalTimeNotNullConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalTime s, ConstraintValidatorContext constraintValidatorContext) {
        return !(s == null);
    }
}
