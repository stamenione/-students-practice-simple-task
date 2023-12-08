package com.example.MealOrder.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AdminDoesNotExistException.class)
    public ResponseEntity<Object> handleAdminDoesNotExistException(
            AdminDoesNotExistException ex, WebRequest request
    ) {
        return new ResponseEntity<>(ex.getErrorResource(), ex.getErrorResource().getCode());
    }

    @ExceptionHandler(MenuAlreadyExistsException.class)
    public ResponseEntity<Object> handleMenuAlreadyExistsException(
            MenuAlreadyExistsException ex, WebRequest request
    ) {
        return new ResponseEntity<>(ex.getErrorResource(), ex.getErrorResource().getCode());
    }

    @ExceptionHandler(MenuNotAvailableException.class)
    public ResponseEntity<Object> handleMenuNotAvailableException(
            MenuNotAvailableException ex, WebRequest request
    ) {
        return new ResponseEntity<>(ex.getErrorResource(), ex.getErrorResource().getCode());
    }

    @ExceptionHandler(NotMondayException.class)
    public ResponseEntity<Object> handleNotMondayException(
            NotMondayException ex, WebRequest request
    ) {

        return new ResponseEntity<>(ex.getErrorResource(), ex.getErrorResource().getCode());
    }

    @ExceptionHandler(PriceLessThanOrEqualToZeroException.class)
    public ResponseEntity<Object> handlePriceLessThanOrEqualToZeroException(
            PriceLessThanOrEqualToZeroException ex, WebRequest request
    ) {

        return new ResponseEntity<>(ex.getErrorResource(), ex.getErrorResource().getCode());
    }

    @ExceptionHandler(NotAvailableOnWeekendException.class)
    public ResponseEntity<Object> handleWeekendException(
            NotAvailableOnWeekendException ex, WebRequest request
    ) {

        return new ResponseEntity<>(ex.getErrorResource(), ex.getErrorResource().getCode());
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Object> handleCityNotFoundException(
            EmailAlreadyExistsException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getErrorResource(), ex.getErrorResource().getCode());
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<Object> handleNodataFoundException(
            WrongPasswordException ex, WebRequest request) {

        return new ResponseEntity<>(ex.getErrorResource(), ex.getErrorResource().getCode());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleNodataFoundException(
            UserNotFoundException ex, WebRequest request) {

        return new ResponseEntity<>(ex.getErrorResource(), ex.getErrorResource().getCode());
    }

    @ExceptionHandler(MealNotFoundException.class)
    public ResponseEntity<Object> handleNodataFoundException(
            MealNotFoundException ex, WebRequest request) {

        return new ResponseEntity<>(ex.getErrorResource(), ex.getErrorResource().getCode());
    }

    @ExceptionHandler(MenuNotFoundException.class)
    public ResponseEntity<Object> handleNodataFoundException(
            MenuNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getErrorResource(), ex.getErrorResource().getCode());
    }

    @ExceptionHandler(FieldsAreEmptyException.class)
    public ResponseEntity<Object> handleNodataFoundException(
            FieldsAreEmptyException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getErrorResource(), ex.getErrorResource().getCode());
    }

    @ExceptionHandler(MealInMenuNotFoundException.class)
    public ResponseEntity<Object> handleNodataFoundException(
            MealInMenuNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getErrorResource(), ex.getErrorResource().getCode());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDate.now());
        body.put("status", status.value());

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyTheChoosenOneException.class)
    public ResponseEntity<Object> handleAlreadyTheChoosenOneException(
            AlreadyTheChoosenOneException ex, WebRequest request
    ) {
        return new ResponseEntity<>(ex.getErrorResource(), ex.getErrorResource().getCode());
    }

    @ExceptionHandler(TheChoosenOneDoesNotExistException.class)
    public ResponseEntity<Object> handleTheChoosenOneDoesNotExistException(
            TheChoosenOneDoesNotExistException ex, WebRequest request
    ) {

        return new ResponseEntity<>(ex.getErrorResource(), ex.getErrorResource().getCode());
    }

    @ExceptionHandler(OrdersNotFoundException.class)
    public ResponseEntity<Object> handleOrdersNotFoundException(
            OrdersNotFoundException ex, WebRequest request
    ) {

        return new ResponseEntity<>(ex.getErrorResource(), ex.getErrorResource().getCode());
    }

    @ExceptionHandler(MealTypesNotFoundException.class)
    public ResponseEntity<Object> handleMealTypesNotFoundException(
            MealTypesNotFoundException ex, WebRequest request
    ) {

        return new ResponseEntity<>(ex.getErrorResource(), ex.getErrorResource().getCode());
    }

    @ExceptionHandler(PasswordFieldIsEmptyException.class)
    public ResponseEntity<Object> handlePasswordFieldIsEmpty(
            PasswordFieldIsEmptyException ex, WebRequest request
    ) {

        return new ResponseEntity<>(ex.getErrorResource(), ex.getErrorResource().getCode());
    }

    @ExceptionHandler(NotSameWeekException.class)
    public ResponseEntity<Object> handleNodataFoundException(
            NotSameWeekException ex, WebRequest request) {

        return new ResponseEntity<>(ex.getErrorResource(), ex.getErrorResource().getCode());
    }

    @ExceptionHandler(UserOrderEmptyException.class)
    public ResponseEntity<Object> handleUserOrderEmptyException(
            UserOrderEmptyException ex, WebRequest request) {

        return new ResponseEntity<>(ex.getErrorResource(), ex.getErrorResource().getCode());
    }


    @ExceptionHandler(RestaurantAlreadyExistsException.class)
    public ResponseEntity<Object> handleRestaurantAlreadyExistsException(
            RestaurantAlreadyExistsException ex, WebRequest request) {

        return new ResponseEntity<>(ex.getErrorResource(), ex.getErrorResource().getCode());
    }


    @ExceptionHandler(RestaurantIsEmptyException.class)
    public ResponseEntity<Object> handleRestaurantIsEmptyException(
            RestaurantIsEmptyException ex, WebRequest request) {

        return new ResponseEntity<>(ex.getErrorResource(), ex.getErrorResource().getCode());
    }

    @ExceptionHandler(CustomMealNotFoundException.class)
    public ResponseEntity<Object> handleCustomMealNotFoundException(
            CustomMealNotFoundException ex, WebRequest request) {

        return new ResponseEntity<>(ex.getErrorResource(), ex.getErrorResource().getCode());
    }

    @ExceptionHandler(CustomRestaurantNotFoundException.class)
    public ResponseEntity<Object> handleCustomRestaurantNotFoundException(
            CustomRestaurantNotFoundException ex, WebRequest request) {

        return new ResponseEntity<>(ex.getErrorResource(), ex.getErrorResource().getCode());
    }

    @ExceptionHandler(RestaurantMealNotFoundException.class)
    public ResponseEntity<Object> handleRestaurantMealNotFoundException(
            RestaurantMealNotFoundException ex, WebRequest request) {

        return new ResponseEntity<>(ex.getErrorResource(), ex.getErrorResource().getCode());
    }


    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<Object> handleRestaurantNotFoundException(
            RestaurantNotFoundException ex, WebRequest request) {

        return new ResponseEntity<>(ex.getErrorResource(), ex.getErrorResource().getCode());
    }


    @ExceptionHandler(InvalidTimeException.class)
    public ResponseEntity<Object> handleInvalidTimeException(
            InvalidTimeException ex, WebRequest request) {

        return new ResponseEntity<>(ex.getErrorResource(), ex.getErrorResource().getCode());
    }
}