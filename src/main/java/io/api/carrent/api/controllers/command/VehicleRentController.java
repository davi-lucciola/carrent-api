package io.api.carrent.api.controllers.command;

import io.api.carrent.domain.dto.input.*;
import io.api.carrent.domain.dto.output.MessageDTO;
import io.api.carrent.domain.entities.User;
import io.api.carrent.domain.services.command.IVehicleRentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/vehicles")
public class VehicleRentController {
    private final IVehicleRentService vehicleRentService;

    @PostMapping("/{id}/rents")
    @SecurityRequirement(name = SECURITY_BEARER)
    @Operation(description = "Reserva um veículo para alugar. Alterando o status de 'Disponivel' para 'Reservado'.")
    public ResponseEntity<MessageDTO> bookVehicleRent(@PathVariable("id") Long vehicleId) {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        vehicleRentService.bookRent(new BookVehicleRentDTO(vehicleId, user));
        return new ResponseEntity<>(new MessageDTO("Veículo reservado com sucesso."), HttpStatus.CREATED);
    }

    @PostMapping("/rents/{id}/confirm")
    @PreAuthorize(ROLE_ADMIN)
    @SecurityRequirement(name = SECURITY_BEARER)
    @Operation(description = "Confirma a reserva do veículo. Alterando o status de 'Reservado' para 'Alugado' ")
    public ResponseEntity<MessageDTO> confirmVehicleRent(
            @PathVariable("id") Long rentId, @RequestBody @Valid RentCodeDTO body
    ) {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        vehicleRentService.confirmRent(new ConfirmVehicleRentDTO(body.code(), rentId, user));
        return new ResponseEntity<>(new MessageDTO("Veículo alugado com sucesso."), HttpStatus.ACCEPTED);
    }

    @PostMapping("/rents/{id}/cancel")
    @SecurityRequirement(name = SECURITY_BEARER)
    @Operation(description = "Cancela a reserva do veículo. Alterando o status de 'Reservado' para 'Disponivel' ")
    public ResponseEntity<MessageDTO> cancelVehicleRent(
            @PathVariable("id") Long rentId, @RequestBody @Valid CancellationReasonDTO body
    ) {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        vehicleRentService.cancelRent(new CancelVehicleRentDTO(rentId, user, body.cancellationReason()));
        return new ResponseEntity<>(new MessageDTO("Reserva do veículo cancelada com sucesso."), HttpStatus.ACCEPTED);
    }

    @PostMapping("/rents/{id}/finish")
    @PreAuthorize(ROLE_ADMIN)
    @SecurityRequirement(name = SECURITY_BEARER)
    @Operation(description = "Finaliza a reserva do veículo. Alterando o status de 'Alugado' para 'Disponivel' ")
    public ResponseEntity<MessageDTO> finishVehicleRent(
            @PathVariable("id") Long rentId, @RequestBody @Valid RentCodeDTO body
    ) {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        vehicleRentService.finishRent(new ReturnVehicleRentDTO(body.code(), rentId, user));
        return new ResponseEntity<>(new MessageDTO("Veículo devolvido com sucesso."), HttpStatus.ACCEPTED);
    }
}
