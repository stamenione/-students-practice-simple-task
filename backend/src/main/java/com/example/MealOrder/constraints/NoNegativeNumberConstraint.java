package com.example.MealOrder.constraints;

import com.example.MealOrder.validators.NoNegativeNumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = NoNegativeNumberValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NoNegativeNumberConstraint {
    String message() default "Number cannot be negative";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
