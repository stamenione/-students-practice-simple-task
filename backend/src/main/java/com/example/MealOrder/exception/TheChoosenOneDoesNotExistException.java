package com.example.MealOrder.exception;

public class TheChoosenOneDoesNotExistException extends CommonException {

    public TheChoosenOneDoesNotExistException(ErrorResource errorResource) {
        this.errorResource = errorResource;
    }

}
