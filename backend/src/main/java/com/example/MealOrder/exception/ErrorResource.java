package com.example.MealOrder.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResource {

    private String message;
    private HttpStatus code;
    private String field;
    private LocalDate timestamp;

    public ErrorResource(String message, HttpStatus code, LocalDate now) {
        this.message = message;
        this.code = code;
        timestamp = now;
    }

}
