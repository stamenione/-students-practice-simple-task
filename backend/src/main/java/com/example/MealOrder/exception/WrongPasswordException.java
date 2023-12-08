package com.example.MealOrder.exception;

public class WrongPasswordException extends CommonException {
    public WrongPasswordException(ErrorResource errorResource) {
        this.errorResource = errorResource;
    }
}
