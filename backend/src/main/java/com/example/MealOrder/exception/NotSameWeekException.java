package com.example.MealOrder.exception;

public class NotSameWeekException extends CommonException {
    public NotSameWeekException(ErrorResource errorResource) {
        this.errorResource = errorResource;
    }
}
