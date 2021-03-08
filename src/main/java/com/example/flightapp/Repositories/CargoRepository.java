package com.example.flightapp.Repositories;

import com.example.flightapp.Entities.CargoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CargoRepository extends JpaRepository<CargoEntity, Long> {
}
