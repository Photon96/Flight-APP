package com.example.flightapp.Assemblers;

import com.example.flightapp.Controlers.FlightController;
import com.example.flightapp.DTO.FlightStatsDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FlightStatsDTOAssembler
        implements RepresentationModelAssembler<FlightStatsDTO, EntityModel<FlightStatsDTO>> {

    @Override
    public EntityModel<FlightStatsDTO> toModel(FlightStatsDTO entity) {

        return EntityModel.of(entity,
                linkTo(methodOn(FlightController.class)
                        .getFlightStats(entity.getFlightNumber(), entity.getDate().toDate())).withSelfRel(),
                linkTo(methodOn(FlightController.class).allFlights()).withRel("/"));
    }
}
