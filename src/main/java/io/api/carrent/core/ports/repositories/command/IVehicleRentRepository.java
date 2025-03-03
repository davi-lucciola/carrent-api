package io.api.carrent.core.ports.repositories.command;

import io.api.carrent.domain.entities.VehicleRent;

import java.util.List;
import java.util.Optional;

public interface IVehicleRentRepository {
    List<VehicleRent> findAllExpired();
    VehicleRent save(VehicleRent vehicleRent);
    Optional<VehicleRent> findById(Long vehicleId);
}
