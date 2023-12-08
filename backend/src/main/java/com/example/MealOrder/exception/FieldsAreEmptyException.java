package com.example.MealOrder.exception;

public class FieldsAreEmptyException extends CommonException {

    public FieldsAreEmptyException(ErrorResource errorResource) {
        this.errorResource = errorResource;
    }
}
