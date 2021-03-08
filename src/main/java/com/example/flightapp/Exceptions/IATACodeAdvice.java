package com.example.flightapp.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class IATACodeAdvice {

    @ResponseBody
    @ExceptionHandler(IATACodeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String iataCodeHandler(IATACodeException ex) {
        return ex.getMessage();
    }
}
