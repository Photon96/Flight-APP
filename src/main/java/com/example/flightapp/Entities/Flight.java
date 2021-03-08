package com.example.flightapp.Entities;

import com.example.flightapp.JsonHandlers.CustomJsonDateDeserializer;
import com.example.flightapp.Enums.IATACode;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Flight {

    @JsonProperty("flightId")
    @Getter private @Id Long id;

    @JsonProperty("flightNumber")
    @Getter private Long number;

    @JsonProperty("departureAirportIATACode")
    @Getter private IATACode departureAirport;

    @JsonProperty("arrivalAirportIATACode")
    @Getter private IATACode arrivalAirport;

    @JsonProperty("departureDate")
    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    @Column(length = 1000)
    @JsonFormat(pattern="yyyy-MM-dd")
    @Getter private DateTime departureDate;

    @JsonIgnore
    @Getter private LocalDate departureLocalDate;

    private final String toStringFormat =
            "{flightId = %s, flightNumber = %s, departureAirport = %s, arrivalAirport = %s, " +
                    "departureFullDate = %s, departureLocalDate = %s}";

    public void setLocalDate() {
        this.departureLocalDate = this.departureDate.toLocalDate();
    }

    @Override
    public String toString() {
        return String.format(toStringFormat,
                this.id, this.number, this.departureAirport, this.arrivalAirport, this.departureDate, this.departureLocalDate);
    }
}
