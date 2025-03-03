package io.api.carrent.core.services.queries;

import io.api.carrent.core.ports.repositories.queries.IVehicleQueryRepository;
import io.api.carrent.domain.dto.input.VehicleQueryDTO;
import io.api.carrent.domain.dto.input.VehicleStatusQueryDTO;
import io.api.carrent.domain.dto.output.Pagination;
import io.api.carrent.domain.dto.output.VehicleDTO;
import io.api.carrent.domain.dto.output.VehicleStatusDTO;
import io.api.carrent.domain.exceptions.NoContentException;
import io.api.carrent.domain.exceptions.NotFoundException;
import io.api.carrent.domain.services.queries.IVehicleQueryService;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleQueryService implements IVehicleQueryService {
    private final IVehicleQueryRepository vehicleQueryRepository;

    @Override
    public Pagination<VehicleDTO> findAll(VehicleQueryDTO filter) {
        List<VehicleDTO> vehicles = vehicleQueryRepository.findAll(filter);

        if (vehicles.isEmpty()) {
            throw new NoContentException();
        }

        return new Pagination<>(vehicles, vehicles.get(0).getTotal(), filter.getPage(), filter.getPerPage());
    }

    @Override
    public Pagination<VehicleStatusDTO> findAllStatus(VehicleStatusQueryDTO filter) {
        List<VehicleStatusDTO> vehicleStatus = vehicleQueryRepository.findAllStatusHistory(filter);

        if (vehicleStatus.isEmpty()) {
            throw new NoContentException();
        }

        return new Pagination<>(vehicleStatus, vehicleStatus.get(0).getTotal(), filter.getPage(), filter.getPerPage());
    }

    @Override
    public VehicleDTO findById(Long vehicleId) {
        try {
            return vehicleQueryRepository.findById(vehicleId);
        } catch (NoResultException | EmptyResultDataAccessException e) {
            throw new NotFoundException("Veículo não encontrado.");
        }
    }
}
