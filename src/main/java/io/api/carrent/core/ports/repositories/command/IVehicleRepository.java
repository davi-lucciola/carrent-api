package io.api.carrent.core.ports.repositories.command;

import io.api.carrent.domain.entities.Vehicle;

import java.util.List;
import java.util.Optional;

public interface IVehicleRepository {
    Optional<Vehicle> findById(Long id);
    Optional<Vehicle> findByPlate(String plate);
    List<Vehicle> findAllByVehicleType(Integer vehicleTypeId);
    Vehicle save(Vehicle vehicle);
}
