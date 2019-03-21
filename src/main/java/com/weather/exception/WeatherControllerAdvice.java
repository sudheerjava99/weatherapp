package com.weather.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WeatherControllerAdvice {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<String> handleExecutionRestrictionViolationException(AppException ex) {
        String response = "{\"error\" : \"" + "City not found" + "\"," + "\"reason\" : \"" + ex.getMessage() + "\"}";
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}