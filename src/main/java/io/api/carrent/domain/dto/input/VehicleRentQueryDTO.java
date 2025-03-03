package io.api.carrent.domain.dto.input;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VehicleRentQueryDTO extends VehicleRentQueryBasicDTO {
    private Long renterUserId;

    public VehicleRentQueryDTO(VehicleRentQueryBasicDTO basicDTO, Long renterUserId) {
        this.page = basicDTO.getPage();
        this.perPage = basicDTO.getPerPage();
        this.search = basicDTO.getSearch();
        this.status = basicDTO.getStatus();
        this.renterUserId = renterUserId;
    }
}
