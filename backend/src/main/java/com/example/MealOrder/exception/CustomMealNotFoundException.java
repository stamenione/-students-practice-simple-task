package com.example.MealOrder.exception;

public class CustomMealNotFoundException extends CommonException {

    public CustomMealNotFoundException(ErrorResource errorResource) {
        this.errorResource = errorResource;
    }

}
