package io.api.carrent.services;

import io.api.carrent.domain.services.VehicleService;
import io.api.carrent.domain.dto.input.SaveVehicleDTO;
import io.api.carrent.domain.dto.output.VehicleDTO;
import io.api.carrent.domain.entities.Vehicle;
import io.api.carrent.domain.entities.VehicleType;
import io.api.carrent.domain.enums.VehicleStatus;
import io.api.carrent.domain.exceptions.DomainException;
import io.api.carrent.domain.exceptions.NotFoundException;
import io.api.carrent.infra.repositories.jpa.VehicleJpaRepository;
import io.api.carrent.infra.repositories.jpa.VehicleTypeJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {

    @Mock
    private VehicleJpaRepository vehicleRepository;

    @Mock
    private VehicleTypeJpaRepository vehicleTypeRepository;

    @InjectMocks
    private VehicleService vehicleService;

    @Test
    void findAllShouldReturnListOfVehicleDTOWhenVehiclesExist() {
        List<VehicleDTO> vehicles = List.of(
                new VehicleDTO(
                        1L,
                        "ABC1234",
                        "Toyota",
                        "Corolla",
                        2022,
                        15000f,
                        "Carro",
                        VehicleStatus.AVAILABLE,
                        true,
                        Instant.now()
                )
        );
        Mockito.when(vehicleRepository.findAllDto()).thenReturn(vehicles);

        List<VehicleDTO> result = vehicleService.findAll();

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(vehicles.size(), result.size());
        Mockito.verify(vehicleRepository, Mockito.times(1)).findAllDto();
    }

    @Test
    void findAllShouldReturnEmptyListWhenNoVehiclesExist() {
        Mockito.when(vehicleRepository.findAllDto()).thenReturn(Collections.emptyList());

        List<VehicleDTO> result = vehicleService.findAll();

        Assertions.assertTrue(result.isEmpty());
        Mockito.verify(vehicleRepository, Mockito.times(1)).findAllDto();
    }

    @Test
    void findByIdShouldReturnVehicleWhenIdExists() {
        Vehicle vehicle = new Vehicle(
                "ABC1234",
                "Toyota",
                "Corolla",
                2022,
                15000f,
                new VehicleType("Sedan")
        );
        Mockito.when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));

        Vehicle result = vehicleService.findById(1L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(vehicle.getPlate(), result.getPlate());
        Mockito.verify(vehicleRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void findByIdShouldThrowNotFoundExceptionWhenIdDoesNotExist() {
        Mockito.when(vehicleRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> vehicleService.findById(1L));
    }

    @Test
    void createShouldCreateAndReturnVehicleWhenDataIsValid() {
        SaveVehicleDTO dto = new SaveVehicleDTO(
                "ABC1234",
                "Toyota",
                "Corolla",
                2022,
                15000f,
                1
        );
        VehicleType vehicleType = new VehicleType("Sedan");

        Mockito.when(vehicleRepository.findByPlate(dto.plate())).thenReturn(Optional.empty());
        Mockito.when(vehicleTypeRepository.findById(dto.vehicleTypeId())).thenReturn(Optional.of(vehicleType));
        Mockito.when(vehicleRepository.save(Mockito.any(Vehicle.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Vehicle result = vehicleService.create(dto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(dto.plate(), result.getPlate());
        Mockito.verify(vehicleRepository, Mockito.times(1)).save(Mockito.any(Vehicle.class));
    }

    @Test
    void createShouldThrowDomainExceptionWhenPlateAlreadyExists() {
        SaveVehicleDTO dto = new SaveVehicleDTO(
                "ABC1234",
                "Toyota",
                "Corolla",
                2022,
                15000f,
                1
        );
        Mockito.when(vehicleRepository.findByPlate(dto.plate())).thenReturn(Optional.of(new Vehicle()));

        Assertions.assertThrows(DomainException.class, () -> vehicleService.create(dto));
    }

    @Test
    void createShouldThrowNotFoundExceptionWhenVehicleTypeDoesNotExist() {
        SaveVehicleDTO dto = new SaveVehicleDTO("ABC1234", "Toyota", "Corolla", 2022, 15000f, 1);
        Mockito.when(vehicleRepository.findByPlate(dto.plate())).thenReturn(Optional.empty());
        Mockito.when(vehicleTypeRepository.findById(dto.vehicleTypeId())).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> vehicleService.create(dto));
    }

    @Test
    void updateShouldUpdateAndReturnVehicleWhenDataIsValid() {
        SaveVehicleDTO dto = new SaveVehicleDTO("DEF5678", "Honda", "Civic", 2021, 18000f, 1);
        Vehicle vehicle = new Vehicle("ABC1234", "Toyota", "Corolla", 2022, 15000f, new VehicleType("Sedan"));
        VehicleType newType = new VehicleType("Hatchback");

        Mockito.when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
        Mockito.when(vehicleRepository.findByPlate(dto.plate())).thenReturn(Optional.empty());
        Mockito.when(vehicleTypeRepository.findById(dto.vehicleTypeId())).thenReturn(Optional.of(newType));
        Mockito.when(vehicleRepository.save(Mockito.any(Vehicle.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Vehicle result = vehicleService.update(1L, dto);

        Assertions.assertEquals(dto.plate(), result.getPlate());
        Mockito.verify(vehicleRepository, Mockito.times(1)).save(Mockito.any(Vehicle.class));
    }

    @Test
    void updateShouldThrowNotFoundExceptionWhenVehicleDoesNotExist() {
        SaveVehicleDTO dto = new SaveVehicleDTO("DEF5678", "Honda", "Civic", 2021, 18000f, 1);
        Mockito.when(vehicleRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> vehicleService.update(1L, dto));
    }

    @Test
    void activateShouldActivateVehicleWhenVehicleExists() {
        Vehicle vehicle = new Vehicle();
        vehicle.deactivate();

        Mockito.when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));

        vehicleService.activate(1L);

        Assertions.assertTrue(vehicle.getFlActive());
        Mockito.verify(vehicleRepository, Mockito.times(1)).save(vehicle);
    }

    @Test
    void activateShouldThrowNotFoundExceptionWhenVehicleDoesNotExist() {
        Mockito.when(vehicleRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> vehicleService.activate(1L));
    }

    @Test
    void deactivateShouldDeactivateVehicleWhenVehicleExists() {
        Vehicle vehicle = new Vehicle();
        vehicle.activate();

        Mockito.when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));

        vehicleService.deactivate(1L);

        Assertions.assertFalse(vehicle.getFlActive());
        Mockito.verify(vehicleRepository, Mockito.times(1)).save(vehicle);
    }

    @Test
    void deactivateShouldThrowNotFoundExceptionWhenVehicleDoesNotExist() {
        Mockito.when(vehicleRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> vehicleService.deactivate(1L));
    }
}
