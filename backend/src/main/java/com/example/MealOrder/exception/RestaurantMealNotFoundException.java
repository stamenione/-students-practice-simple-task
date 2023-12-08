package com.example.MealOrder.exception;

public class RestaurantMealNotFoundException extends CommonException {

    public RestaurantMealNotFoundException(ErrorResource errorResource) {
        this.errorResource = errorResource;
    }

}
