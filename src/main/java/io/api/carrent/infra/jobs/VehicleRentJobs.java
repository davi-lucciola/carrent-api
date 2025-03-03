package io.api.carrent.infra.jobs;

import io.api.carrent.core.ports.repositories.command.IVehicleRentRepository;
import io.api.carrent.domain.entities.VehicleRent;
import io.api.carrent.domain.services.command.IVehicleRentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class VehicleRentJobs {
    private final IVehicleRentService vehicleRentService;
    private final IVehicleRentRepository vehicleRentRepository;

    // 15 Minutos
    @Scheduled(fixedDelay = 1000 * 60 * 15L)
    public void cancelExpiredRentsJob() {
        String cancellationReason = "Tempo Expirado";
        log.info("[INICIANDO][JOB: CANCELAMENTO DE ALUGUEIS EXPIRADOS]");

        List<VehicleRent> expiredRents = vehicleRentRepository.findAllExpired();
        expiredRents.forEach(vehicleRent ->
                vehicleRentService.cancelRent(vehicleRent, null, cancellationReason));

        log.info("[CONCLUIDO][JOB: CANCELAMENTO DE ALUGUEIS EXPIRADOS]");
    }
}
