package io.api.carrent.services.queries;

import io.api.carrent.core.ports.repositories.queries.IVehicleQueryRepository;
import io.api.carrent.core.services.queries.VehicleQueryService;
import io.api.carrent.domain.dto.output.VehicleDTO;
import io.api.carrent.domain.dto.output.VehicleTypeDTO;
import io.api.carrent.domain.enums.VehicleStatus;
import io.api.carrent.domain.exceptions.NoContentException;
import io.api.carrent.domain.exceptions.NotFoundException;
import jakarta.persistence.NoResultException;
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

@ExtendWith(MockitoExtension.class)
public class VehicleQueryServiceTest {
    @Mock
    private IVehicleQueryRepository vehicleQueryRepository;

    @InjectMocks
    private VehicleQueryService vehicleQueryService;

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
                        VehicleStatus.AVAILABLE,
                        new VehicleTypeDTO(2, "Carro"),
                        true,
                        Instant.now()
                )
        );
        Mockito.when(vehicleQueryRepository.findAll()).thenReturn(vehicles);

        List<VehicleDTO> result = vehicleQueryService.findAll();

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(vehicles.size(), result.size());
        Mockito.verify(vehicleQueryRepository, Mockito.times(1)).findAll();
    }

    @Test
    void findAllShouldReturnEmptyListWhenNoVehiclesExist() {
        Mockito.when(vehicleQueryRepository.findAll()).thenReturn(Collections.emptyList());

        Assertions.assertThrows(NoContentException.class, () -> vehicleQueryService.findAll());
        Mockito.verify(vehicleQueryRepository, Mockito.times(1)).findAll();
    }

    @Test
    void findByIdShouldReturnVehicleWhenIdExists() {
        VehicleDTO vehicle = new VehicleDTO(
                1L,
                "ABC1234",
                "Toyota",
                "Corolla",
                2022,
                15000f,
                VehicleStatus.AVAILABLE,
                new VehicleTypeDTO(2, "Carro"),
                true,
                Instant.now()
        );
        Mockito.when(vehicleQueryRepository.findById(1L)).thenReturn(vehicle);

        VehicleDTO result = vehicleQueryService.findById(1L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(vehicle.getPlate(), result.getPlate());
        Mockito.verify(vehicleQueryRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void findByIdShouldThrowNotFoundExceptionWhenIdDoesNotExist() {
        Mockito.when(vehicleQueryRepository.findById(1L)).thenThrow(new NoResultException());

        Assertions.assertThrows(NotFoundException.class, () -> vehicleQueryService.findById(1L));
    }

}
