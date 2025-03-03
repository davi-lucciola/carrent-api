package io.api.carrent.app.ports.repositories.command;

import io.api.carrent.domain.entities.VehicleType;

import java.util.Optional;

public interface IVehicleTypeRepository {
    VehicleType save(VehicleType vehicleType);
    void delete(VehicleType vehicleType);
    Optional<VehicleType> findById(Integer id);
    Optional<VehicleType> findByName(String name);
}
