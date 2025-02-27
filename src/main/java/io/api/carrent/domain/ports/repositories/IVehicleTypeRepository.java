package io.api.carrent.domain.ports.repositories;

import io.api.carrent.domain.entities.VehicleType;

import java.util.List;
import java.util.Optional;

public interface IVehicleTypeRepository {
    List<VehicleType> findAll();
    Optional<VehicleType> findById(Integer id);
    Optional<VehicleType> findByName(String name);
    VehicleType save(VehicleType vehicleType);
    void delete(VehicleType vehicleType);
}
