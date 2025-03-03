package io.api.carrent.app.services.queries;

import io.api.carrent.app.ports.repositories.queries.IVehicleTypeQueryRepository;
import io.api.carrent.domain.dto.output.VehicleTypeDTO;
import io.api.carrent.domain.services.queries.IVehicleTypeQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleTypeQueryService implements IVehicleTypeQueryService {
    private final IVehicleTypeQueryRepository vehicleTypeQueryRepository;

    @Override
    public List<VehicleTypeDTO> findAll() {
        return vehicleTypeQueryRepository.findAll();
    }
}
