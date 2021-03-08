package com.example.flightapp.Assemblers;

import com.example.flightapp.Controlers.FlightController;
import com.example.flightapp.Entities.Flight;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FlightAssembler
        implements RepresentationModelAssembler<Flight, EntityModel<Flight>> {

    @Override
    public EntityModel<Flight> toModel(Flight flight) {
        return EntityModel.of(flight,
                linkTo(methodOn(FlightController.class).getFlightStats(
                        flight.getNumber(), flight.getDepartureDate().toDate())).withSelfRel(),
                linkTo(methodOn(FlightController.class).allFlights()).withRel("/"));
    }
}
