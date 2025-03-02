package io.api.carrent.domain.services.command;

import io.api.carrent.domain.dto.input.SaveVehicleDTO;
import io.api.carrent.domain.entities.Vehicle;

public interface IVehicleService {
    Vehicle create(SaveVehicleDTO saveVehicleDTO);
    Vehicle update(Long vehicleId, SaveVehicleDTO saveVehicleDTO);
    void activate(Long vehicleId);
    void deactivate(Long vehicleId);
}
