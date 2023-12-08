package com.example.MealOrder.exception;

public class OrdersNotFoundException extends CommonException {

    public OrdersNotFoundException(ErrorResource errorResource) {
        this.errorResource = errorResource;
    }

}
