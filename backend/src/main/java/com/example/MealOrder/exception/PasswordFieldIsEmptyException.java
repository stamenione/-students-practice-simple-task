package com.example.MealOrder.exception;

public class PasswordFieldIsEmptyException extends CommonException {

    public PasswordFieldIsEmptyException(ErrorResource errorResource) {
        this.errorResource = errorResource;
    }
}
