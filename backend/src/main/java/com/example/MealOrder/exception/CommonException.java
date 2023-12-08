package com.example.MealOrder.exception;

public class CommonException extends RuntimeException {

    protected ErrorResource errorResource;

    public ErrorResource getErrorResource() {
        return this.errorResource;
    }

}
