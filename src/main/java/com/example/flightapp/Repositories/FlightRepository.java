package com.example.flightapp.Repositories;

import com.example.flightapp.Entities.Flight;
import com.example.flightapp.IATACode;
import org.joda.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    List<Flight> findAllByArrivalAirportAndDepartureLocalDate(
            IATACode arrivalAirport, LocalDate departureDate);
    List<Flight> findAllByDepartureAirportAndDepartureLocalDate(
            IATACode departureAirport, LocalDate departureDate);
    @Query("select f from Flight f where f.number=:flightNumber and f.departureLocalDate=:departureDate")
    Optional<Flight> findByFlightNumberAndDate(Long flightNumber, LocalDate departureDate);
}
