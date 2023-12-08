package com.example.MealOrder.exception;

public class RestaurantNotFoundException extends CommonException {

    public RestaurantNotFoundException(ErrorResource errorResource) {
        this.errorResource = errorResource;
    }
}
