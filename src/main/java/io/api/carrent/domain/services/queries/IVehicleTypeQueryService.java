package io.api.carrent.domain.services.queries;

import io.api.carrent.domain.dto.output.VehicleTypeDTO;

import java.util.List;

public interface IVehicleTypeQueryService {
    List<VehicleTypeDTO> findAll();
}
