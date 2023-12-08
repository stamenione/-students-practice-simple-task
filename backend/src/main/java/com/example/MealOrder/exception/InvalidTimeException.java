package com.example.MealOrder.exception;

public class InvalidTimeException extends CommonException {

    public InvalidTimeException(ErrorResource errorResource) {
        this.errorResource = errorResource;
    }
}
