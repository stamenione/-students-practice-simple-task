package com.example.MealOrder.exception;

public class CustomRestaurantNotFoundException extends CommonException {

    public CustomRestaurantNotFoundException(ErrorResource errorResource) {
        this.errorResource = errorResource;
    }

}
