package com.example.MealOrder.exception;

public class MenuAlreadyExistsException extends CommonException {

    public MenuAlreadyExistsException(ErrorResource errorResource) {
        this.errorResource = errorResource;
    }

}
