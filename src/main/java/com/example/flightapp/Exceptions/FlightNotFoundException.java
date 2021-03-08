package com.example.flightapp.Exceptions;

import org.joda.time.LocalDate;

public class FlightNotFoundException extends RuntimeException {
    public FlightNotFoundException(Long id, LocalDate localDate) {
        super("Could not find flight " + id + " on " + localDate);
    }
}
