package com.example.MealOrder.exception;

public class AlreadyTheChoosenOneException extends CommonException {

    public AlreadyTheChoosenOneException(ErrorResource errorResource) {
        this.errorResource = errorResource;
    }

}
