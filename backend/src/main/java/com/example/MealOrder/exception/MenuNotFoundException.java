package com.example.MealOrder.exception;

public class MenuNotFoundException extends CommonException {
    public MenuNotFoundException(ErrorResource errorResource) {
        this.errorResource = errorResource;
    }

}
