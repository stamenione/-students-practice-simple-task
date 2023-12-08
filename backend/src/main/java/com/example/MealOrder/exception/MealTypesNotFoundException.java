package com.example.MealOrder.exception;

public class MealTypesNotFoundException extends CommonException {

    public MealTypesNotFoundException(ErrorResource errorResource) {
        this.errorResource = errorResource;
    }

}
