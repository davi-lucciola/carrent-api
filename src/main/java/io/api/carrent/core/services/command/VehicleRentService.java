package io.api.carrent.core.services.command;

import io.api.carrent.core.ports.repositories.command.IVehicleRepository;
import io.api.carrent.core.ports.repositories.command.IVehicleRentRepository;
import io.api.carrent.domain.dto.input.CancelVehicleRentDTO;
import io.api.carrent.domain.entities.User;
import io.api.carrent.domain.services.command.IVehicleRentService;
import io.api.carrent.domain.dto.input.BookVehicleRentDTO;
import io.api.carrent.domain.dto.input.ConfirmVehicleRentDTO;
import io.api.carrent.domain.dto.input.ReturnVehicleRentDTO;
import io.api.carrent.domain.entities.Vehicle;
import io.api.carrent.domain.entities.VehicleRent;
import io.api.carrent.domain.exceptions.DomainException;
import io.api.carrent.domain.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleRentService implements IVehicleRentService {
    private final IVehicleRepository vehicleRepository;
    private final IVehicleRentRepository vehicleRentRepository;

    @Override
    public void bookRent(BookVehicleRentDTO bookVehicleRentDTO) {
        Vehicle vehicle = vehicleRepository.findById(bookVehicleRentDTO.vehicleId())
                .orElseThrow(() -> new NotFoundException("Veículo não encontrado"));

        if (Boolean.FALSE.equals(vehicle.getFlActive())) {
            throw new DomainException("Não é possivel reservar um carro inativo.");
        }

        VehicleRent vehicleRent = new VehicleRent(vehicle);
        vehicleRent.bookVehicle(bookVehicleRentDTO.renterUser());

        vehicleRentRepository.save(vehicleRent);
    }

    @Override
    public void cancelRent(CancelVehicleRentDTO cancelVehicleRentDTO) {
        VehicleRent vehicleRentWaiting = vehicleRentRepository.findById(cancelVehicleRentDTO.rentId())
                .orElseThrow(() -> new NotFoundException("Não foi encontrado uma reserva para esse veículo."));

        User operatorUser = null;
        User currentUser = cancelVehicleRentDTO.currentUser();

        boolean existsCurrentUser = currentUser != null;

        if (existsCurrentUser) {
            boolean isAdminUser = currentUser.isAdmin();
            if (isAdminUser) operatorUser = currentUser;

            boolean isNotRenterUser = Boolean.FALSE.equals(
                    vehicleRentWaiting.getRenter().getId().equals(currentUser.getId()));

            if (Boolean.FALSE.equals(isAdminUser) && isNotRenterUser) {
                throw new DomainException("Você não pode cancelar a reserva que não é sua.");
            }
        }

        vehicleRentWaiting.cancelRent(cancelVehicleRentDTO.cancellationReason(), operatorUser);
        vehicleRentRepository.save(vehicleRentWaiting);
    }

    @Override
    public void confirmRent(ConfirmVehicleRentDTO confirmVehicleRentDTO) {
        VehicleRent vehicleRentWaiting = vehicleRentRepository.findById(confirmVehicleRentDTO.rentId())
                .orElseThrow(() -> new NotFoundException("Não foi encontrado uma reserva para esse veículo."));

        if (Boolean.FALSE.equals(vehicleRentWaiting.getVehicle().getFlActive())) {
            throw new DomainException("Não é possivel alugar um carro inativo.");
        }

        if (!vehicleRentWaiting.verifyWithdrawCode(confirmVehicleRentDTO.withdrawCode())) {
            throw new DomainException("Código de confirmação da reserva inválido.");
        }

        vehicleRentWaiting.startRent(confirmVehicleRentDTO.withdrawOperatorUser());
        vehicleRentRepository.save(vehicleRentWaiting);
    }

    @Override
    public void finishRent(ReturnVehicleRentDTO returnVehicleRentDTO) {
        VehicleRent vehicleRentInProgress = vehicleRentRepository.findById(returnVehicleRentDTO.rentId())
                .orElseThrow(() -> new NotFoundException("Não foi encontrado um aluguel em andamento para esse veículo."));

        if (!vehicleRentInProgress.verifyReturnCode(returnVehicleRentDTO.returnCode())) {
            throw new DomainException("Código de retorno do veículo inválido.");
        }

        vehicleRentInProgress.finishRent(returnVehicleRentDTO.returnOperatorUser());
        vehicleRentRepository.save(vehicleRentInProgress);
    }
}
