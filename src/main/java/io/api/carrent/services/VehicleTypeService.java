package io.api.carrent.services;

import io.api.carrent.dto.input.SaveVehicleTypeDTO;
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
public class VehicleTypeService {
    private final VehicleJpaRepository vehicleRepository;
    private final VehicleTypeJpaRepository vehicleTypeRepository;

    public List<VehicleType> findAll() {
        return vehicleTypeRepository.findAll();
    }

    public VehicleType create(SaveVehicleTypeDTO vehicleTypeDTO) {
        Optional<VehicleType> vehicleTypeOptional = vehicleTypeRepository.findByName(vehicleTypeDTO.name());

        if (vehicleTypeOptional.isPresent()) {
            throw new DomainException("Já existe um tipo de veículo cadastrado com esse nome.");
        }

        VehicleType vehicleType = new VehicleType(vehicleTypeDTO.name());
        vehicleTypeRepository.saveAndFlush(vehicleType);
        return vehicleType;
    }

    public VehicleType update(Integer vehicleTypeId, SaveVehicleTypeDTO vehicleTypeDTO) {
        Optional<VehicleType> vehicleTypeOptional = vehicleTypeRepository.findById(vehicleTypeId);

        if (vehicleTypeOptional.isEmpty()) {
            throw new NotFoundException("Tipo de veículo não encontrado.");
        }

        VehicleType vehicleType = vehicleTypeOptional.get();
        vehicleTypeOptional = vehicleTypeRepository.findByName(vehicleTypeDTO.name());

        boolean isOtherVehicleType = vehicleTypeOptional.isPresent()
                && !vehicleType.getId().equals(vehicleTypeOptional.get().getId());

        if (isOtherVehicleType) {
            throw new DomainException("Já existe um tipo de veículo cadastrado com esse nome.");
        }

        vehicleType.setName(vehicleTypeDTO.name());
        vehicleTypeRepository.saveAndFlush(vehicleType);
        return vehicleType;
    }

    public void delete(Integer vehicleTypeId) {
        Optional<VehicleType> vehicleTypeOptional = vehicleTypeRepository.findById(vehicleTypeId);

        if (vehicleTypeOptional.isEmpty()) {
            throw new NotFoundException("Tipo de veículo não encontrado.");
        }

        VehicleType vehicleType = vehicleTypeOptional.get();
        List<Vehicle> vehicles = vehicleRepository.findAllByVehicleType(vehicleType.getId());

        if (!vehicles.isEmpty()) {
            throw new DomainException("Não é possivel excluir tipo de veículo pois existem veículos pertencentes a esse tipo.");
        }

        vehicleTypeRepository.delete(vehicleType);
    }
}
