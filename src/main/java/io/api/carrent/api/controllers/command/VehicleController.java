package io.api.carrent.api.controllers.command;

import io.api.carrent.domain.dto.input.SaveVehicleDTO;
import io.api.carrent.domain.dto.output.MessageDTO;
import io.api.carrent.domain.services.command.IVehicleService;
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
import static io.api.carrent.api.docs.SwaggerConstants.VEHICLE_TAG;

@Tag(name = VEHICLE_TAG)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vehicles")
public class VehicleController {
    private final IVehicleService vehicleService;

    @PostMapping
    @PreAuthorize(ROLE_ADMIN)
    @SecurityRequirement(name = SECURITY_BEARER)
    @Operation(description = "Cadastra um novo veículo. (ADMIN)")
    public ResponseEntity<MessageDTO> createVehicle(@RequestBody @Valid SaveVehicleDTO saveVehicleDTO) {
        vehicleService.create(saveVehicleDTO);
        return new ResponseEntity<>(new MessageDTO("Veículo cadastrado com sucesso."), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize(ROLE_ADMIN)
    @SecurityRequirement(name = SECURITY_BEARER)
    @Operation(description = "Atualiza um veículo existente. (ADMIN)")
    public ResponseEntity<MessageDTO> updateVehicle(
            @PathVariable Long id, @RequestBody @Valid SaveVehicleDTO saveVehicleDTO
    ) {
        vehicleService.update(id, saveVehicleDTO);
        return new ResponseEntity<>(new MessageDTO("Veículo atualizado com sucesso."), HttpStatus.ACCEPTED);
    }

    @PatchMapping("/{id}/activate")
    @PreAuthorize(ROLE_ADMIN)
    @SecurityRequirement(name = SECURITY_BEARER)
    @Operation(description = "Ativa um veículo desativado. (ADMIN)")
    public ResponseEntity<MessageDTO> activateVehicle(@PathVariable Long id) {
        vehicleService.activate(id);
        return new ResponseEntity<>(new MessageDTO("Veículo ativado com sucesso."), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}/deactivate")
    @PreAuthorize(ROLE_ADMIN)
    @SecurityRequirement(name = SECURITY_BEARER)
    @Operation(description = "Desativa um veículo ativo. (ADMIN)")
    public ResponseEntity<MessageDTO> deactivateVehicle(@PathVariable Long id) {
        vehicleService.deactivate(id);
        return new ResponseEntity<>(new MessageDTO("Veículo desativado com sucesso."), HttpStatus.ACCEPTED);
    }
}
