package io.api.carrent.controllers;

import io.api.carrent.dto.input.SaveVehicleTypeDTO;
import io.api.carrent.dto.output.MessageDTO;
import io.api.carrent.entities.VehicleType;
import io.api.carrent.services.VehicleTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static io.api.carrent.config.SecurityConstants.ROLE_ADMIN;
import static io.api.carrent.config.SwaggerConstants.SECURITY_BEARER;
import static io.api.carrent.config.SwaggerConstants.VEHICLE_TYPE_TAG;

@Tag(name = VEHICLE_TYPE_TAG)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vehicles/types")
public class VehicleTypeController {
    private final VehicleTypeService vehicleTypeService;

    @GetMapping
    @SecurityRequirement(name = SECURITY_BEARER)
    @Operation(description = "Busca o todos os tipos de veículos .")
    public ResponseEntity<List<VehicleType>> findAllVehicleTypes() {
        return new ResponseEntity<>(vehicleTypeService.findAll(), HttpStatus.OK);
    }

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
