package com.example.MealOrder.exception;

public class NotAvailableOnWeekendException extends CommonException {

    public NotAvailableOnWeekendException(ErrorResource errorResource) {
        this.errorResource = errorResource;
    }

}
