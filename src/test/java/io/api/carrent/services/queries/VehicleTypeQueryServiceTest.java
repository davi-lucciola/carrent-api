package io.api.carrent.services.queries;

import io.api.carrent.app.ports.repositories.queries.IVehicleTypeQueryRepository;
import io.api.carrent.app.services.queries.VehicleTypeQueryService;
import io.api.carrent.domain.dto.output.VehicleTypeDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class VehicleTypeQueryServiceTest {
    @Mock
    private IVehicleTypeQueryRepository vehicleTypeQueryRepository;

    @InjectMocks
    private VehicleTypeQueryService vehicleTypeQueryService;

    @Test
    void shouldReturnEmptyListWhenNoVehicleTypesExist() {
        // Arrange
        Mockito.when(vehicleTypeQueryRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<VehicleTypeDTO> result = vehicleTypeQueryService.findAll();

        // Assert
        Assertions.assertTrue(result.isEmpty());
        Mockito.verify(vehicleTypeQueryRepository).findAll();
    }

    @Test
    void shouldReturnListOfVehicleTypesWhenTheyExist() {
        // Arrange
        List<VehicleTypeDTO> vehicleTypes = List.of(new VehicleTypeDTO(1, "Carro"), new VehicleTypeDTO(2, "Moto"));
        Mockito.when(vehicleTypeQueryRepository.findAll()).thenReturn(vehicleTypes);

        // Act
        List<VehicleTypeDTO> result = vehicleTypeQueryService.findAll();

        // Assert
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Carro", result.get(0).getName());
        Assertions.assertEquals("Moto", result.get(1).getName());
        Mockito.verify(vehicleTypeQueryRepository).findAll();
    }

}
