package com.example.flightapp.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.joda.time.LocalDate;

@RequiredArgsConstructor
public class FlightStatsDTO {

    @Getter private final Long flightNumber;
    @JsonFormat(pattern="yyyy-MM-dd")
    @Getter private final LocalDate date;
    @Getter private final int cargoWeight;
    @Getter private final int baggageWeight;
    @Getter private final int totalWeight;
    @Getter private final String unit = "kg";

}
