package com.example.MealOrder.exception;

public class EmailAlreadyExistsException extends CommonException {

    public EmailAlreadyExistsException(ErrorResource errorResource) {
        this.errorResource = errorResource;
    }
}
