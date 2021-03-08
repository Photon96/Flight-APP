package com.example.flightapp.Exceptions;

public class CargoNotFoundException extends RuntimeException {
    public CargoNotFoundException(Long id) {
        super("Cargo for flight " + id + " wasn't found.");
    }
}
