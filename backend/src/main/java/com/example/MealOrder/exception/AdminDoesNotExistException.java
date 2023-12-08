package com.example.MealOrder.exception;

public class AdminDoesNotExistException extends CommonException {


    public AdminDoesNotExistException(ErrorResource errorResource) {
        this.errorResource = errorResource;
    }

}
