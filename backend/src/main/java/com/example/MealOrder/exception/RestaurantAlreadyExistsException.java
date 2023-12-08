package com.example.MealOrder.exception;

public class RestaurantAlreadyExistsException extends CommonException {

    public RestaurantAlreadyExistsException(ErrorResource errorResource) {
        this.errorResource = errorResource;
    }
}
