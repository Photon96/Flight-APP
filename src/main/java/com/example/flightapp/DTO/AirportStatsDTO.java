package com.example.flightapp.DTO;

import com.example.flightapp.IATACode;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.joda.time.LocalDate;

@RequiredArgsConstructor
public class AirportStatsDTO {

    @Getter private final IATACode iataCode;
    @JsonFormat(pattern="yyyy-MM-dd")
    @Getter private final LocalDate date;
    @Getter private final int nrFlightsDeparting;
    @Getter private final int nrFlightsArriving;
    @Getter private final int piecesBaggageDeparting;
    @Getter private final int piecesBaggageArriving;

}
