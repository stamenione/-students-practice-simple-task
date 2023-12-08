package com.example.MealOrder.exception;

public class UserOrderEmptyException extends CommonException {
    public UserOrderEmptyException(ErrorResource errorResource) {
        this.errorResource = errorResource;
    }
}
