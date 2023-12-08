package com.example.MealOrder.exception;

public class MenuNotAvailableException extends CommonException {

    public MenuNotAvailableException(ErrorResource errorResource) {
        this.errorResource = errorResource;
    }

}
