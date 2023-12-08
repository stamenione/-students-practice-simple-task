package com.example.MealOrder.exception;

public class MealInMenuNotFoundException extends CommonException {

    public MealInMenuNotFoundException(ErrorResource errorResource) {
        this.errorResource = errorResource;
    }
}
