package com.example.MealOrder.exception;

public class RestaurantIsEmptyException extends CommonException {

    public RestaurantIsEmptyException(ErrorResource errorResource) {
        this.errorResource = errorResource;
    }
}
