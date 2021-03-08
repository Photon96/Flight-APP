package com.example.flightapp;

import com.example.flightapp.Entities.CargoEntity;
import com.example.flightapp.Entities.Flight;
import com.example.flightapp.Repositories.CargoRepository;
import com.example.flightapp.Repositories.FlightRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initFlightDatabase(FlightRepository repository) {
        try {

            ObjectMapper mapper = new ObjectMapper();
            File resource = new ClassPathResource("data/flight_entity.json").getFile();
            List<Flight> flights = Arrays.asList(mapper.readValue(resource, Flight[].class));
            flights.forEach(Flight::setLocalDate);
            flights.forEach(repository::save);

            return args -> {
                for (Flight flight : flights) {
                    log.info("Preloading " + flight.toString());
                }
            };

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return args -> {
            log.info("Error occurred when flight database was loaded");
        };
    }

    @Bean
    CommandLineRunner initCargoDatabase(CargoRepository repository) {
        try {

            ObjectMapper mapper = new ObjectMapper();
            File resource = new ClassPathResource("data/cargo_entity.json").getFile();

            List<CargoEntity> cargoEntities = Arrays.asList(mapper.readValue(resource, CargoEntity[].class));

            cargoEntities.forEach(System.out::println);
            cargoEntities.forEach(repository::save);

            return args -> {
                for (CargoEntity cargoEntity : cargoEntities) {
                    log.info("Preloading " + cargoEntity.toString());
                }
            };

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return args -> {
            log.info("Error occurred when cargo database was loaded");
        };
    }
}
