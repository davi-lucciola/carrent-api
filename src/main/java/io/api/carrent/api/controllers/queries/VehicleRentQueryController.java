package io.api.carrent.api.controllers.queries;

import io.api.carrent.domain.dto.input.VehicleRentQueryBasicDTO;
import io.api.carrent.domain.dto.input.VehicleRentQueryDTO;
import io.api.carrent.domain.dto.output.Pagination;
import io.api.carrent.domain.dto.output.VehicleRentDTO;
import io.api.carrent.domain.dto.output.VehicleRentDetailDTO;
import io.api.carrent.domain.entities.User;
import io.api.carrent.domain.services.queries.IVehicleRentQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static io.api.carrent.api.docs.SwaggerConstants.RENT_VEHICLE_TAG;
import static io.api.carrent.api.docs.SwaggerConstants.SECURITY_BEARER;
import static io.api.carrent.infra.security.SecurityConstants.ROLE_ADMIN;

@Tag(name = RENT_VEHICLE_TAG)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vehicles/rents")
public class VehicleRentQueryController {
    private final IVehicleRentQueryService vehicleRentQueryService;

    @GetMapping
    @PreAuthorize(ROLE_ADMIN)
    @SecurityRequirement(name = SECURITY_BEARER)
    @Operation(description = "Busca todos os alugueis de veículos.")
    public ResponseEntity<Pagination<VehicleRentDTO>> findAllRents(@ParameterObject VehicleRentQueryBasicDTO query) {
        var filter = new VehicleRentQueryDTO(query, null);
        return new ResponseEntity<>(vehicleRentQueryService.findAll(filter), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = SECURITY_BEARER)
    @Operation(description = "Endpoint para detalhar um aluguel pelo id.")
    public ResponseEntity<VehicleRentDetailDTO> findRentById(@PathVariable("id") Long rentId) {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(vehicleRentQueryService.findById(rentId, user), HttpStatus.OK);
    }

    @GetMapping("/my")
    @SecurityRequirement(name = SECURITY_BEARER)
    @Operation(description = "Busca todos os alugueis de veículos feitos pelo usuário atual.")
    public ResponseEntity<Pagination<VehicleRentDTO>> findAllMyRents(@ParameterObject VehicleRentQueryBasicDTO query) {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var filter = new VehicleRentQueryDTO(query, user.getId());
        return new ResponseEntity<>(vehicleRentQueryService.findAll(filter), HttpStatus.OK);
    }
}
