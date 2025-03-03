package io.api.carrent.services.commands;

import io.api.carrent.core.ports.repositories.command.IVehicleRepository;
import io.api.carrent.core.ports.repositories.command.IVehicleTypeRepository;
import io.api.carrent.core.services.command.VehicleTypeService;
import io.api.carrent.domain.dto.input.SaveVehicleTypeDTO;
import io.api.carrent.domain.entities.Vehicle;
import io.api.carrent.domain.entities.VehicleType;
import io.api.carrent.domain.exceptions.DomainException;
import io.api.carrent.domain.exceptions.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class VehicleTypeServiceTest {

    @Mock
    private IVehicleRepository vehicleRepository;

    @Mock
    private IVehicleTypeRepository vehicleTypeRepository;

    @InjectMocks
    private VehicleTypeService vehicleTypeService;

    @Test
    void shouldCreateVehicleTypeWhenNameIsUnique() {
        // Arrange
        SaveVehicleTypeDTO vehicleTypeDTO = new SaveVehicleTypeDTO("Caminhão");
        VehicleType vehicleType = new VehicleType(1, "Caminhão");

        Mockito.when(vehicleTypeRepository.findByName(vehicleTypeDTO.name())).thenReturn(Optional.empty());
        Mockito.when(vehicleTypeRepository.save(Mockito.any(VehicleType.class))).thenReturn(vehicleType);

        // Act
        VehicleType result = vehicleTypeService.create(vehicleTypeDTO);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(vehicleTypeDTO.name(), result.getName());
        Mockito.verify(vehicleTypeRepository).findByName(vehicleTypeDTO.name());
        Mockito.verify(vehicleTypeRepository).save(Mockito.any(VehicleType.class));
    }

    @Test
    void shouldThrowExceptionWhenVehicleTypeAlreadyExists() {
        // Arrange
        SaveVehicleTypeDTO vehicleTypeDTO = new SaveVehicleTypeDTO("Caminhão");
        VehicleType existingVehicleType = new VehicleType(1, "Caminhão");

        Mockito.when(vehicleTypeRepository.findByName(vehicleTypeDTO.name())).thenReturn(Optional.of(existingVehicleType));

        // Act & Assert
        Assertions.assertThrows(DomainException.class, () -> vehicleTypeService.create(vehicleTypeDTO));

        Mockito.verify(vehicleTypeRepository).findByName(vehicleTypeDTO.name());
        Mockito.verifyNoMoreInteractions(vehicleTypeRepository);
    }

    @Test
    void shouldUpdateVehicleTypeSuccessfully() {
        // Arrange
        Integer vehicleTypeId = 1;
        SaveVehicleTypeDTO vehicleTypeDTO = new SaveVehicleTypeDTO("Novo Nome");
        VehicleType existingVehicleType = new VehicleType(vehicleTypeId, "Antigo Nome");

        Mockito.when(vehicleTypeRepository.findById(vehicleTypeId)).thenReturn(Optional.of(existingVehicleType));
        Mockito.when(vehicleTypeRepository.findByName(vehicleTypeDTO.name())).thenReturn(Optional.empty());
        Mockito.when(vehicleTypeRepository.save(Mockito.any(VehicleType.class))).thenReturn(existingVehicleType);

        // Act
        VehicleType result = vehicleTypeService.update(vehicleTypeId, vehicleTypeDTO);

        // Assert
        Assertions.assertEquals(vehicleTypeDTO.name(), result.getName());
        Mockito.verify(vehicleTypeRepository).findById(vehicleTypeId);
        Mockito.verify(vehicleTypeRepository).findByName(vehicleTypeDTO.name());
        Mockito.verify(vehicleTypeRepository).save(existingVehicleType);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenUpdatingNonExistentVehicleType() {
        // Arrange
        Integer vehicleTypeId = 99;
        SaveVehicleTypeDTO vehicleTypeDTO = new SaveVehicleTypeDTO("Novo Nome");

        Mockito.when(vehicleTypeRepository.findById(vehicleTypeId)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(NotFoundException.class, () -> vehicleTypeService.update(vehicleTypeId, vehicleTypeDTO));

        Mockito.verify(vehicleTypeRepository).findById(vehicleTypeId);
        Mockito.verifyNoMoreInteractions(vehicleTypeRepository);
    }

    @Test
    void shouldThrowDomainExceptionWhenUpdatingToAnExistingName() {
        // Arrange
        Integer vehicleTypeId = 1;
        SaveVehicleTypeDTO vehicleTypeDTO = new SaveVehicleTypeDTO("Duplicado");
        VehicleType existingVehicleType = new VehicleType(vehicleTypeId, "Antigo Nome");
        VehicleType anotherVehicleType = new VehicleType(2, "Duplicado");

        Mockito.when(vehicleTypeRepository.findById(vehicleTypeId)).thenReturn(Optional.of(existingVehicleType));
        Mockito.when(vehicleTypeRepository.findByName(vehicleTypeDTO.name())).thenReturn(Optional.of(anotherVehicleType));

        // Act & Assert
        Assertions.assertThrows(DomainException.class, () -> vehicleTypeService.update(vehicleTypeId, vehicleTypeDTO));

        Mockito.verify(vehicleTypeRepository).findById(vehicleTypeId);
        Mockito.verify(vehicleTypeRepository).findByName(vehicleTypeDTO.name());
        Mockito.verifyNoMoreInteractions(vehicleTypeRepository);
    }

    @Test
    void shouldDeleteVehicleTypeSuccessfully() {
        // Arrange
        Integer vehicleTypeId = 1;
        VehicleType vehicleType = new VehicleType(vehicleTypeId, "Carro");

        Mockito.when(vehicleTypeRepository.findById(vehicleTypeId)).thenReturn(Optional.of(vehicleType));
        Mockito.when(vehicleRepository.findAllByVehicleType(vehicleTypeId)).thenReturn(Collections.emptyList());

        // Act
        vehicleTypeService.delete(vehicleTypeId);

        // Assert
        Mockito.verify(vehicleTypeRepository).findById(vehicleTypeId);
        Mockito.verify(vehicleRepository).findAllByVehicleType(vehicleTypeId);
        Mockito.verify(vehicleTypeRepository).delete(vehicleType);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenDeletingNonExistentVehicleType() {
        // Arrange
        Integer vehicleTypeId = 99;

        Mockito.when(vehicleTypeRepository.findById(vehicleTypeId)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(NotFoundException.class, () -> vehicleTypeService.delete(vehicleTypeId));

        Mockito.verify(vehicleTypeRepository).findById(vehicleTypeId);
        Mockito.verifyNoMoreInteractions(vehicleTypeRepository);
    }

    @Test
    void shouldThrowDomainExceptionWhenDeletingVehicleTypeWithVehicles() {
        // Arrange
        Integer vehicleTypeId = 1;
        VehicleType vehicleType = new VehicleType(vehicleTypeId, "Carro");
        List<Vehicle> associatedVehicles = List.of(
                new Vehicle("ABC1234", "Ford", "Mustang", 2015, 100.3F, vehicleType)
        );
        associatedVehicles.get(0).setId(1L);

        Mockito.when(vehicleTypeRepository.findById(vehicleTypeId)).thenReturn(Optional.of(vehicleType));
        Mockito.when(vehicleRepository.findAllByVehicleType(vehicleTypeId)).thenReturn(associatedVehicles);

        // Act & Assert
        Assertions.assertThrows(DomainException.class, () -> vehicleTypeService.delete(vehicleTypeId));

        Mockito.verify(vehicleTypeRepository).findById(vehicleTypeId);
        Mockito.verify(vehicleRepository).findAllByVehicleType(vehicleTypeId);
        Mockito.verifyNoMoreInteractions(vehicleTypeRepository);
    }
}

