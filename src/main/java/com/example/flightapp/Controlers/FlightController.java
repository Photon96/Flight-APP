package com.example.flightapp.Controlers;

import com.example.flightapp.*;
import com.example.flightapp.Assemblers.FlightAssembler;
import com.example.flightapp.Assemblers.FlightStatsDTOAssembler;
import com.example.flightapp.DTO.AirportStatsDTO;
import com.example.flightapp.DTO.FlightStatsDTO;
import com.example.flightapp.Entities.Baggage;
import com.example.flightapp.Entities.Cargo;
import com.example.flightapp.Entities.CargoEntity;
import com.example.flightapp.Entities.Flight;
import com.example.flightapp.Exceptions.CargoNotFoundException;
import com.example.flightapp.Exceptions.FlightNotFoundException;
import com.example.flightapp.Exceptions.IATACodeException;
import com.example.flightapp.Repositories.CargoRepository;
import com.example.flightapp.Repositories.FlightRepository;
import lombok.AllArgsConstructor;
import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@AllArgsConstructor
public class FlightController {

    private final FlightRepository flightRepository;
    private final CargoRepository cargoRepository;
    private final FlightStatsDTOAssembler flightStatsAssembler;
    private final FlightAssembler flightAssembler;


    @GetMapping("/")
    public CollectionModel<EntityModel<Flight>> allFlights() {
        List<EntityModel<Flight>> flights = flightRepository.findAll().stream()
                .map(this.flightAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(flights, linkTo(methodOn(FlightController.class).allFlights()).withSelfRel());
    }

    @GetMapping("airports")
    public EntityModel<AirportStatsDTO> getAirportStats(@RequestParam(name = "iata") String iata,
                                                 @RequestParam(name = "date")
                                                 @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) throws IATACodeException {
        IATACode iataCode;
        try {
            iataCode = IATACode.valueOf(iata);
        } catch (IllegalArgumentException ex) {
            throw new IATACodeException(iata);
        }
        LocalDate localDate = LocalDate.fromDateFields(date);
        List<Flight> arrivingFlights = flightRepository.findAllByArrivalAirportAndDepartureLocalDate(
                iataCode, localDate);
        List<Flight> departingFlights = flightRepository.findAllByDepartureAirportAndDepartureLocalDate(
                iataCode, localDate);

        int nrFlightsArriving = arrivingFlights.size();
        int nrFlightsDeparting = departingFlights.size();

        int piecesBaggageArriving = getPiecesBaggage(arrivingFlights);
        int piecesBaggageDeparting = getPiecesBaggage(departingFlights);

        AirportStatsDTO airportStats = new AirportStatsDTO(
                iataCode, localDate, nrFlightsDeparting, nrFlightsArriving, piecesBaggageDeparting, piecesBaggageArriving);

        return EntityModel.of(airportStats, linkTo(methodOn(FlightController.class).getAirportStats(
                airportStats.getIataCode().toString(), airportStats.getDate().toDate())).withSelfRel(),
                linkTo(methodOn(FlightController.class).allFlights()).withRel(("/")));
    }

    private int getPiecesBaggage(List<Flight> flights) {
        if (flights.isEmpty())
            return 0;
        int piecesBaggage = 0;
        for (Flight flight : flights) {
            Optional<CargoEntity> cargoEntity = cargoRepository.findById(flight.getId());
            if (cargoEntity.isPresent()) {
                piecesBaggage += calculatePiecesBaggage(cargoEntity.get());
            }
        }
        return piecesBaggage;
    }

    private int calculatePiecesBaggage(CargoEntity cargoEntity) {
        return cargoEntity
                .getBaggage()
                .stream()
                .mapToInt(Baggage::getPieces)
                .sum();
    }

    @GetMapping("flights")
    public EntityModel<FlightStatsDTO> getFlightStats(@RequestParam(name = "flightNumber") Long flightNumber,
                                  @RequestParam(name = "date")
                                  @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {

        LocalDate localDate = LocalDate.fromDateFields(date);

        Flight flight = flightRepository.findByFlightNumberAndDate(flightNumber, localDate)
                .orElseThrow(() -> new FlightNotFoundException(flightNumber, localDate));

        CargoEntity cargoEntity = cargoRepository.findById(flight.getId())
                .orElseThrow(() -> new CargoNotFoundException(flightNumber));

        int cargoWeight = calculateCargoWeight(cargoEntity.getCargo());
        int baggageWeight = calculateBaggageWeight(cargoEntity.getBaggage());
        int totalWeight = cargoWeight + baggageWeight;

        FlightStatsDTO flightStats = new FlightStatsDTO(flightNumber, localDate, cargoWeight, baggageWeight, totalWeight);

        return flightStatsAssembler.toModel(flightStats);
    }

    private int calculateCargoWeight(List<Cargo> cargo) {
        return (int)cargo.stream()
                .mapToDouble(Cargo::getWeightInKg)
                .sum();
    }

    private int calculateBaggageWeight(List<Baggage> baggage) {
        return (int)baggage.stream()
                .mapToDouble(Baggage::getWeightInKg)
                .sum();
    }
}
