package io.api.carrent.services;

import io.api.carrent.dto.input.SaveVehicleDTO;
import io.api.carrent.dto.output.VehicleDTO;
import io.api.carrent.entities.Vehicle;
import io.api.carrent.entities.VehicleType;
import io.api.carrent.exceptions.DomainException;
import io.api.carrent.exceptions.NotFoundException;
import io.api.carrent.repositories.VehicleJpaRepository;
import io.api.carrent.repositories.VehicleTypeJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleJpaRepository vehicleRepository;
    private final VehicleTypeJpaRepository vehicleTypeRepository;

    public List<VehicleDTO> findAll() {
        return vehicleRepository.findAllDto();
    }

    public Vehicle findById(Long vehicleId) {
        return vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new NotFoundException("Veículo não encontrado"));
    }

    public Vehicle create(SaveVehicleDTO saveVehicleDTO) {
        Optional<Vehicle> vehiclePlate = vehicleRepository.findByPlate(saveVehicleDTO.plate());

        if (vehiclePlate.isPresent()) {
            throw new DomainException("Já existe um veículo cadastrado com a placa '%s'".formatted(saveVehicleDTO.plate()));
        }

        VehicleType vehicleType = vehicleTypeRepository
                .findById(saveVehicleDTO.vehicleTypeId())
                .orElseThrow(() -> new NotFoundException("Tipo de veículo não encontrado."));

        Vehicle vehicle = new Vehicle(saveVehicleDTO.plate(), saveVehicleDTO.brand(),
                saveVehicleDTO.model(), saveVehicleDTO.year(), saveVehicleDTO.odometer(), vehicleType);

        vehicleRepository.saveAndFlush(vehicle);
        return vehicle;
    }

    public Vehicle update(Long vehicleId, SaveVehicleDTO saveVehicleDTO) {
        Vehicle vehicle = this.findById(vehicleId);
        Optional<Vehicle> vehiclePlate = vehicleRepository.findByPlate(saveVehicleDTO.plate());

        boolean isOtherVehicleWithExistentPlate =
                vehiclePlate.isPresent() && !vehicle.getId().equals(vehiclePlate.get().getId());

        if (isOtherVehicleWithExistentPlate) {
            throw new DomainException("Já existe um veículo cadastrado com a placa '%s'".formatted(saveVehicleDTO.plate()));
        }

        VehicleType vehicleType = vehicleTypeRepository
                .findById(saveVehicleDTO.vehicleTypeId())
                .orElseThrow(() -> new NotFoundException("Tipo de veículo não encontrado."));

        vehicle.update(saveVehicleDTO.plate(), saveVehicleDTO.brand(),
                saveVehicleDTO.model(), saveVehicleDTO.year(), saveVehicleDTO.odometer(), vehicleType);

        vehicleRepository.saveAndFlush(vehicle);
        return vehicle;
    }
}
