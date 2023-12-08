package com.example.MealOrder.exception;

public class UserNotFoundException extends CommonException {

    public UserNotFoundException(ErrorResource errorResource) {
        this.errorResource = errorResource;
    }
}
