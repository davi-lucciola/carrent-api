package io.api.carrent.domain.services.command;

import io.api.carrent.domain.dto.input.BookVehicleRentDTO;
import io.api.carrent.domain.dto.input.CancelVehicleRentDTO;
import io.api.carrent.domain.dto.input.ConfirmVehicleRentDTO;
import io.api.carrent.domain.dto.input.ReturnVehicleRentDTO;

public interface IVehicleRentService {
    void bookRent(BookVehicleRentDTO bookVehicleRentDTO);
    void cancelRent(CancelVehicleRentDTO confirmVehicleRentDTO);
    void confirmRent(ConfirmVehicleRentDTO confirmVehicleRentDTO);
    void finishRent(ReturnVehicleRentDTO returnVehicleRentDTO);
}
