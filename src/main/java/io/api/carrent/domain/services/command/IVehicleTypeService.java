package io.api.carrent.domain.services.command;


import io.api.carrent.domain.dto.input.SaveVehicleTypeDTO;
import io.api.carrent.domain.entities.VehicleType;


public interface IVehicleTypeService {
    VehicleType create(SaveVehicleTypeDTO vehicleTypeDTO);
    VehicleType update(Integer id, SaveVehicleTypeDTO vehicleTypeDTO);
    void delete(Integer id);
}
