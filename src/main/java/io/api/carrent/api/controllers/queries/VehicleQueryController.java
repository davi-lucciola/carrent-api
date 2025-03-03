package io.api.carrent.api.controllers.queries;

import io.api.carrent.domain.dto.input.PaginationParams;
import io.api.carrent.domain.dto.input.VehicleQueryDTO;
import io.api.carrent.domain.dto.input.VehicleStatusQueryDTO;
import io.api.carrent.domain.dto.output.Pagination;
import io.api.carrent.domain.dto.output.VehicleDTO;
import io.api.carrent.domain.dto.output.VehicleStatusDTO;
import io.api.carrent.domain.services.queries.IVehicleQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static io.api.carrent.api.docs.SwaggerConstants.SECURITY_BEARER;
import static io.api.carrent.api.docs.SwaggerConstants.VEHICLE_TAG;
import static io.api.carrent.infra.security.SecurityConstants.ROLE_ADMIN;

@Tag(name = VEHICLE_TAG)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vehicles")
public class VehicleQueryController {
    private final IVehicleQueryService vehicleQueryService;

    @GetMapping
    @SecurityRequirement(name = SECURITY_BEARER)
    @Operation(description = "Busca o todos os veículos.")
    public ResponseEntity<Pagination<VehicleDTO>> findAllVehicles(@ParameterObject VehicleQueryDTO filter) {
        return new ResponseEntity<>(vehicleQueryService.findAll(filter), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = SECURITY_BEARER)
    @Operation(description = "Busca um veículo pelo id.")
    public ResponseEntity<VehicleDTO> detailVehicle(@PathVariable Long id) {
        return new ResponseEntity<>(vehicleQueryService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/status/history")
    @PreAuthorize(ROLE_ADMIN)
    @SecurityRequirement(name = SECURITY_BEARER)
    @Operation(description = "Busca o historico de status dos veículos.")
    public ResponseEntity<Pagination<VehicleStatusDTO>> findAllStatusHistories(
            @PathVariable("id") Long vehicleId, @ParameterObject PaginationParams paginationParams
    ) {
        var filter = new VehicleStatusQueryDTO(vehicleId, paginationParams.getPage(), paginationParams.getPerPage());
        return new ResponseEntity<>(vehicleQueryService.findAllStatus(filter), HttpStatus.OK);
    }
}
