package com.example.MealOrder.exception;

public class MealNotFoundException extends CommonException {
    public MealNotFoundException(ErrorResource errorResource) {
        this.errorResource = errorResource;
    }
}
