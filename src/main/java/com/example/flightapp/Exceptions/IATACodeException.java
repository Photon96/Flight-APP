package com.example.flightapp.Exceptions;

public class IATACodeException extends Exception {
    public IATACodeException(String iata) {
        super("Airport " + iata + " doesn't exist");
    }
}
