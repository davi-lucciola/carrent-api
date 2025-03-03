package io.api.carrent.app.services.command;

import io.api.carrent.app.ports.repositories.command.IVehicleRepository;
import io.api.carrent.app.ports.repositories.command.IVehicleTypeRepository;
import io.api.carrent.domain.dto.input.SaveVehicleDTO;
import io.api.carrent.domain.entities.Vehicle;
import io.api.carrent.domain.entities.VehicleType;
import io.api.carrent.domain.exceptions.DomainException;
import io.api.carrent.domain.exceptions.NotFoundException;
import io.api.carrent.domain.services.command.IVehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VehicleService implements IVehicleService {
    private final IVehicleRepository vehicleRepository;
    private final IVehicleTypeRepository vehicleTypeRepository;

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

        return vehicleRepository.save(vehicle);
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

        return vehicleRepository.save(vehicle);
    }

    public void activate(Long vehicleId) {
        Vehicle vehicle = this.findById(vehicleId);
        vehicle.activate();
        vehicleRepository.save(vehicle);
    }

    public void deactivate(Long vehicleId) {
        Vehicle vehicle = this.findById(vehicleId);
        vehicle.deactivate();
        vehicleRepository.save(vehicle);
    }
}
