package io.api.carrent.app.ports.repositories.command;

import io.api.carrent.domain.entities.Vehicle;

import java.util.List;
import java.util.Optional;

public interface IVehicleRepository {
    Vehicle save(Vehicle vehicle);
    Optional<Vehicle> findById(Long id);
    Optional<Vehicle> findByPlate(String plate);
    List<Vehicle> findAllByVehicleType(Integer vehicleTypeId);
}
