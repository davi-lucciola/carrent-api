package io.api.carrent.domain.ports.repositories;

import io.api.carrent.domain.dto.output.VehicleDTO;
import io.api.carrent.domain.entities.Vehicle;

import java.util.List;
import java.util.Optional;

public interface IVehicleRepository {
    List<VehicleDTO> findAll();
    Optional<Vehicle> findById(Long id);
    Optional<Vehicle> findByPlate(String plate);
    List<Vehicle> findAllByVehicleType(Integer vehicleTypeId);
    Vehicle save(Vehicle vehicle);
}
