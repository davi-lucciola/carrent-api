package io.api.carrent.core.ports.repositories.command;

import io.api.carrent.domain.entities.VehicleType;

import java.util.Optional;

public interface IVehicleTypeRepository {
    Optional<VehicleType> findById(Integer id);
    Optional<VehicleType> findByName(String name);
    VehicleType save(VehicleType vehicleType);
    void delete(VehicleType vehicleType);
}
