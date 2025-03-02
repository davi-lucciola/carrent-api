package io.api.carrent.core.services.queries;

import io.api.carrent.core.ports.repositories.queries.IVehicleTypeQueryRepository;
import io.api.carrent.domain.services.queries.IVehicleTypeQueryService;
import io.api.carrent.domain.dto.output.VehicleTypeDTO;
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
