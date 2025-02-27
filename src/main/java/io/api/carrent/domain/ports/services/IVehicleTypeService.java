package io.api.carrent.domain.ports.services;


import io.api.carrent.domain.dto.input.SaveVehicleTypeDTO;
import io.api.carrent.domain.entities.VehicleType;

import java.util.List;


public interface IVehicleTypeService {
    List<VehicleType> findAll();
    VehicleType create(SaveVehicleTypeDTO vehicleTypeDTO);
    VehicleType update(Integer id, SaveVehicleTypeDTO vehicleTypeDTO);
    void delete(Integer id);
}
