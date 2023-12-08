package com.example.MealOrder.exception;

public class PriceLessThanOrEqualToZeroException extends CommonException {

    public PriceLessThanOrEqualToZeroException(ErrorResource errorResource) {
        this.errorResource = errorResource;
    }

}
