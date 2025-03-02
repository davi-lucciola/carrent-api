package io.api.carrent.api.controllers.command;

import io.api.carrent.domain.dto.input.SaveVehicleTypeDTO;
import io.api.carrent.domain.dto.output.MessageDTO;
import io.api.carrent.domain.entities.VehicleType;
import io.api.carrent.domain.services.command.IVehicleTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static io.api.carrent.infra.security.SecurityConstants.ROLE_ADMIN;
import static io.api.carrent.api.docs.SwaggerConstants.SECURITY_BEARER;
import static io.api.carrent.api.docs.SwaggerConstants.VEHICLE_TYPE_TAG;

@Tag(name = VEHICLE_TYPE_TAG)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vehicles/types")
public class VehicleTypeController {
    private final IVehicleTypeService vehicleTypeService;

    @PostMapping
    @PreAuthorize(ROLE_ADMIN)
    @SecurityRequirement(name = SECURITY_BEARER)
    @Operation(description = "Cadastra um novo tipo de veículo (ADMIN).")
    public ResponseEntity<VehicleType> createVehicleType(@RequestBody @Valid SaveVehicleTypeDTO vehicleTypeDTO) {
        return new ResponseEntity<>(vehicleTypeService.create(vehicleTypeDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize(ROLE_ADMIN)
    @SecurityRequirement(name = SECURITY_BEARER)
    @Operation(description = "Atualiza um tipo de veículo existente (ADMIN).")
    public ResponseEntity<VehicleType> updateVehicleType(
            @PathVariable Integer id, @RequestBody @Valid SaveVehicleTypeDTO vehicleTypeDTO
    ) {
        return new ResponseEntity<>(vehicleTypeService.update(id, vehicleTypeDTO), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(ROLE_ADMIN)
    @SecurityRequirement(name = SECURITY_BEARER)
    @Operation(description = "Excluí um tipo de veículo existente se não estiver associado a veículos (ADMIN).")
    public ResponseEntity<MessageDTO> deleteVehicleType(@PathVariable Integer id) {
        vehicleTypeService.delete(id);
        return new ResponseEntity<>(new MessageDTO("Tipo de veículo excluído com sucesso."), HttpStatus.ACCEPTED);
    }
}
