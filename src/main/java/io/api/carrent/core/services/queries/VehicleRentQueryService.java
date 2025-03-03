package io.api.carrent.core.services.queries;

import io.api.carrent.core.ports.repositories.queries.IVehicleRentQueryRepository;
import io.api.carrent.domain.dto.input.VehicleRentQueryDTO;
import io.api.carrent.domain.dto.output.Pagination;
import io.api.carrent.domain.dto.output.VehicleRentDTO;
import io.api.carrent.domain.exceptions.NoContentException;
import io.api.carrent.domain.services.queries.IVehicleRentQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleRentQueryService implements IVehicleRentQueryService {
    private final IVehicleRentQueryRepository vehicleRentQueryRepository;

    @Override
    public Pagination<VehicleRentDTO> findAll(VehicleRentQueryDTO filter) {
        List<VehicleRentDTO> vehicleRents = vehicleRentQueryRepository.findAll(filter);

        if (vehicleRents.isEmpty()) {
            throw new NoContentException();
        }

        return new Pagination<>(vehicleRents, vehicleRents.get(0).getTotal(), filter.getPage(), filter.getPerPage());

    }
}
