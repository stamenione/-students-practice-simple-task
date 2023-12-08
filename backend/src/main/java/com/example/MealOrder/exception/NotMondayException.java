package com.example.MealOrder.exception;

public class NotMondayException extends CommonException {

    public NotMondayException(ErrorResource errorResource) {
        this.errorResource = errorResource;
    }

}
