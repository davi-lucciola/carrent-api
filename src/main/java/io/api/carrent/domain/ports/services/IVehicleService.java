package io.api.carrent.domain.ports.services;

import io.api.carrent.domain.dto.input.SaveVehicleDTO;
import io.api.carrent.domain.dto.output.VehicleDTO;
import io.api.carrent.domain.entities.Vehicle;

import java.util.List;

public interface IVehicleService {
    List<VehicleDTO> findAll();
    Vehicle findById(Long vehicleId);
    Vehicle create(SaveVehicleDTO saveVehicleDTO);
    Vehicle update(Long vehicleId, SaveVehicleDTO saveVehicleDTO);
    void activate(Long vehicleId);
    void deactivate(Long vehicleId);
}
