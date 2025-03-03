package io.api.carrent.domain.services.command;

import io.api.carrent.domain.dto.input.BookVehicleRentDTO;
import io.api.carrent.domain.dto.input.CancelVehicleRentDTO;
import io.api.carrent.domain.dto.input.ConfirmVehicleRentDTO;
import io.api.carrent.domain.dto.input.ReturnVehicleRentDTO;
import io.api.carrent.domain.entities.User;
import io.api.carrent.domain.entities.VehicleRent;

public interface IVehicleRentService {
    void bookRent(BookVehicleRentDTO bookVehicleRentDTO);
    void confirmRent(ConfirmVehicleRentDTO confirmVehicleRentDTO);
    void finishRent(ReturnVehicleRentDTO returnVehicleRentDTO);
    void cancelRent(CancelVehicleRentDTO cancelVehicleRentDTO);
    void cancelRent(VehicleRent vehicleRentWaiting, User operatorUser, String cancellationReason);
}
